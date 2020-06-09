package renderer;

import elements.*;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.awt.Color;
import java.util.List;


import geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

/**
 * Render class
 * Converts the image from D3 to D2
 * @author avi && daniel
 */
public class Render {
    Scene scene;
    ImageWriter imageWriter;

    /**
     * MAX_CALC_COLOR_LEVEL - maximum level in the recursion three
     */
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    /**
     * MIN_CALC_COLOR_K - stopping calculate color at this value of k
     */
    private static final double MIN_CALC_COLOR_K = 0.001;

    /**
     * _threads - num of threads
     */
    private int _threads = 3;
    /**
     * _print - print percent for debug
     */
    private boolean _print = true;

    private int numOfRays = 30;

    /**
     * Pixel class
     * represent a single pixel
     * internal class to help the multi threads functions
     * @author avi && daniel
     */
    public class Pixel{
        private long _maxRow = 0;
        private long _maxCols = 0;
        private long _pixels = 0;
        public volatile int row = 0;
        public volatile int col = -1;
        private long _counter = 0;
        private int _percent = 0;
        private long _nextCounter = 0;

        /**
         * contractor - gets gets the image params
         * @param maxRow the nx
         * @param maxCols the ny
         */
        public Pixel(int maxRow, int maxCols){
            _maxCols = maxCols;
            _maxRow = maxRow;
            _pixels = maxCols * maxRow;
            _nextCounter = _pixels/100;
            if(Render.this._print)
                System.out.printf("\r %02d%%", _percent);
        }

        /**
         * default contractor
         */
        public Pixel() {}

        /**
         * Pixel - nextPixel
         * return true if there is a next pixel
         */
        public boolean nextPixel(Pixel target) throws InterruptedException {
            int percent = nextp(target);
            if(percent > 0 && Render.this._print) {
                System.out.printf("\r %02d%%", percent);
            }
            if(percent >= 0)
                return true;
            if(Render.this._print){
                System.out.printf("\r %02d%%", 100);
        }
            return false;
        }

        /**
         * Pixel - nextP
         * changing target pixel to the next pixel
         * return 0 if there is a next pixel, and -1 else
         */
        private synchronized int nextp(Pixel target)
        {
            col++;
            _counter++;
            if(col < _maxCols) {
                target.row = this.row;
                target.col = this.col;
                if(_counter == _nextCounter)
                {
                    _percent++;
                    _nextCounter = _pixels * (_percent + 1) / 100;
                    return _percent;
                }
                return 0;
            }

            row++;
            if(row < _maxRow) {
                col = 0;
                if(_counter == _nextCounter)
                {
                    _percent++;
                    _nextCounter = _pixels * (_percent + 1) / 100;
                    return _percent;
                }
                return 0;
            }
            return -1;
        }
    }

    /**
     * contractor - gets gets the image params
     * @param imageWriter the D2 image
     * @param scene the scene to converts
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

    /**
     * contractor - gets gets the image params
     * @param imageWriter the D2 image
     * @param scene the scene to converts
     * @param _print for debug printing
     */
    public Render(ImageWriter imageWriter, Scene scene, boolean _print) {
        this(imageWriter, scene);
        this._print = _print;
    }

    /**
     * Render - Image
     * Converts the image from D3 to D2
     * The result is written in imageWriter
     */
    public void renderImage() throws Exception{
        Camera camera = scene.get_camera();
        Intersectable geometries = scene.get_geometries();
        Color background = scene.get_background().getColor();
        double distance = scene.get_distance();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        double width = imageWriter.getWidth();
        double height = imageWriter.getHeight();

        //Multi threads for calculating pixel color
        final Pixel thePixel = new Pixel(nY,nX);
        Thread[] threads = new Thread[_threads];
        for(int i = 0 ; i<_threads; i++)
        {
            threads[i] = new Thread(()->{
                Pixel pixel = new Pixel();
                try {
                    //while there is a pixel that is not processed by a thread
                    while (thePixel.nextPixel(pixel)) {
                        List<Ray> rays = null;
                        try {
                            rays = camera.constructRaysThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height, numOfRays);
                            imageWriter.writePixel(pixel.col, pixel.row, calcColor(rays).getColor());
                        } catch (Exception e) {
                        }
                    }
                }
                catch (Exception e){}
            });
        }

        //starts all threads
        for (Thread thread : threads)
            thread.start();

        //Wait for all threads to finish
        for (Thread thread : threads)
            thread.join();

        //finish to create the image
        if(_print)
            System.out.print("\r100%\n");
    }

    /**
     * Pixel - setMultiThreading
     * set the number of threads
     */
    public void setMultiThreading(int threads) {
        if(threads <= 0)
            throw new IllegalArgumentException("The thread must be positive number");
        this._threads = threads;


    }

    /**
     * numOfRays - setNumOfRays
     * set the number of rays in each pixel
     */
    public void setNumOfRays(int numOfRays) {
        this.numOfRays = numOfRays;
    }

    /**
     * printGrid - add to the image grid
     * @param i the interval
     * @param color of the grid
     *
     */
    public void printGrid(int i, Color color) {
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        for(int x = 0; x < nX; x++)
        {
            for(int y = 0; y < nY; y++)
            {
                if(x % i == 0 || y % i == 0 || x == nX - 1 || y == nY - 1)
                    imageWriter.writePixel(x , y , color);
            }
        }
    }

    /**
     * writeToImage - save the image
     */
    public void writeToImage() {
        imageWriter.writeToImage();
    }

    /**
     * calcColor - return the intensity in a point
     * @param p the point we calculate color
     * @return the color intensity in the point
     */
    private primitives.Color calcColor(GeoPoint p, Ray ray, int level, double k){
        try {
            if(level == 1 || k <MIN_CALC_COLOR_K)
                return primitives.Color.BLACK;
            primitives.Color color = p.geometry.get_emmission();
            Vector v = p.point.subtract(scene.get_camera().getLocation()).normalize();
            Vector n = p.geometry.getNormal(p.point);
            double nv = alignZero(n.dotProduct(v));
            //if n is orthogonal to v
            if(nv == 0)
                return color;
            Material material = p.geometry.get_material();
            int nShininess = material.get_nShininess();
            double kd = material.get_kD();// level of diffusive
            double ks = material.get_kS();// level of specular
            for (LightSource lightSource : scene.get_lights()){
                Vector l = lightSource.getL(p.point);
                double nl = alignZero(n.dotProduct(l));
                if(nl*nv>0)//if the camera can see
                {
                    double ktr = transparency(l, n, p, lightSource);// level of shadow
                    primitives.Color ip =lightSource.getIntensity(p.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, nl, ip), calcSpecular(ks, l, n, nl, v, nShininess, ip));
                }
            }

            //reflection calculate
            double kr = material.get_KR();
            double kkr = k*kr;
            if(kkr > MIN_CALC_COLOR_K) {
                Ray reflectedRay = constructReflectedRay(n, p.point,ray);
                GeoPoint gp = findClosestIntersection(reflectedRay);
                color = color.add(gp == null? primitives.Color.BLACK : calcColor(gp, reflectedRay, level -1, kkr).scale(kr));
            }

            //transparency calculate
            double kt = material.get_KT();
            double kkt = k*kt;
            if(kkt > MIN_CALC_COLOR_K) {
                Ray refractedRay = constructRefractedRay(n, p.point,ray);
                GeoPoint gp = findClosestIntersection(refractedRay);
                color = color.add(gp == null? primitives.Color.BLACK : calcColor(gp, refractedRay, level -1, kkt).scale(kt));
            }
            return color;
        }
        catch (Exception e)
        {
            return p.geometry.get_emmission();
        }
    }

    /**
     * calcColor - return the color in a point
     * @param gp point
     * @param ray intersection point
     * @return the color intensity in the point
     */
    private primitives.Color calcColor(GeoPoint gp, Ray ray){
        return calcColor(gp, ray, MAX_CALC_COLOR_LEVEL, 1d).add(scene.get_ambientLight().get_intensity());
    }

    /**
     * calcColor - return the average color in a pixel
     * @param rays list of rays throw pixel
     * @return the color intensity in the point
     */
    private primitives.Color calcColor(List<Ray> rays) throws Exception {
        primitives.Color color = primitives.Color.BLACK;
        GeoPoint gp;
        for(Ray ray : rays)
        {
            gp = findClosestIntersection(ray);
            if(gp == null)
                color = color.add(this.scene.get_background());
            else
                color = color.add(calcColor(gp, ray));
        }
        return color.reduce(rays.size());
    }

    /**
     * Calculate specular component
     * @param ks specular component
     * @param l direction from light to point
     * @param n normal to the point
     * @param nl dotProduct n*l
     * @param v direction from camera to point
     * @param nShininess shininess
     * @param ip the light intensity at the point
     * @return specular component in the point
     */
    private primitives.Color calcSpecular(double ks, Vector l, Vector n, double nl, Vector v, int nShininess, primitives.Color ip){
        try {
            Vector r = l.add(n.scale(-2*nl));
            double minusVR = -alignZero(r.dotProduct(v));
            if(minusVR <= 0)
                return primitives.Color.BLACK;
            return ip.scale(ks*Math.pow(minusVR, nShininess));
        }
        catch (Exception e)
        {
            return this.scene.get_background();
        }
    }

    /**
     * Calculate diffusive component
     * @param kd diffusive component
     * @param nl dotProduct n*l
     * @param ip the light intensity at the point
     * @return diffusive component in the point
     */
    private primitives.Color calcDiffusive(double kd, double nl, primitives.Color ip)
    {
        if(nl < 0)
            nl = -nl;
        return ip.scale(nl * kd);
    }

    /**
     * getClosestPoint - calculate the closest intersection point
     * @param points list of Point3D intersection point's
     * @return the closest intersection point
     */
    private GeoPoint getClosestPoint(List<GeoPoint> points){
        if(points == null)
            return null;
        Point3D locationOfCamera = scene.get_camera().getLocation();
        GeoPoint closestPoint = points.get(0);
        for(int i = 1; i < points.size(); i++)
        {
            if(points.get(i).point.distance(locationOfCamera) < closestPoint.point.distance(locationOfCamera))
                closestPoint = points.get(i);
        }
        return closestPoint;
    }

    /**
     * unshaded -cheak if an intersection point need to be shadow or not
     * @param l direction from light to point
     * @param n normal to the point
     * @param gp the point we check for shadow
     * @param lightSource the light source
     * @return true if its an intersection point need to be unshadow
     */
    private boolean unshaded(Vector l, Vector n, GeoPoint gp, LightSource lightSource){
        try {
            Vector fromPointToLightVector = l.scale(-1);
            Ray fromPointToLightRay = new Ray(gp.point, fromPointToLightVector, n);
            List<GeoPoint> intersection = scene.get_geometries().findIntersections(fromPointToLightRay, lightSource.getDistance(gp.point));
            return intersection==null || intersection.size() == 0;
        }
        catch (Exception e){
            return true;
        }
    }

    /**
     * transparency -check if an intersection point need to be shadow or not
     * @param l direction from light to point
     * @param n normal to the point
     * @param geopoint the point we check for shadow
     * @param lightSource the light source
     * @return shadow strength
     */
    private double transparency(Vector l, Vector n, GeoPoint geopoint, LightSource lightSource){
        try {
            Vector fromPointToLightVector = l.scale(-1);
            Ray fromPointToLightRay = new Ray(geopoint.point, fromPointToLightVector, n);
            double lightDistance = lightSource.getDistance(geopoint.point);
            List<GeoPoint> intersections = scene.get_geometries().findIntersections(fromPointToLightRay, lightDistance);
            if(intersections==null || intersections.size() == 0)
                return 1;

            double ktr = 1.0;
            for (GeoPoint gp : intersections) {
                    ktr *= gp.geometry.get_material().get_KT();// kt - transparency
                    if (ktr < MIN_CALC_COLOR_K)
                        return 0.0;
            }
            return ktr;
        }
        catch (Exception e){
            return 0;
        }
    }

    /**
     * contract Reflected Ray
     * @param n normal to point
     * @param point, the intersection point
     * @param ray, the ray we wants his reflected ray
     */
    private Ray constructReflectedRay(Vector n, Point3D point, Ray ray) throws Exception
    {
        double nv = ray.get_vector().dotProduct(n);
        Vector v = ray.get_vector();
        Vector ref = v.add(n.scale(-2*nv));
        return new Ray(point, ref, n);
    }

    /**
     * contract Refracted Ray
     * @param n normal to point
     * @param point, the intersection point
     * @param ray, the ray we wants his refracted ray
     */
    private Ray constructRefractedRay(Vector n, Point3D point, Ray ray) throws Exception
    {
        return new Ray(point, ray.get_vector(), n);
    }

    /**
     * findClosestIntersection - find the closest point to the ray head
     * @param ray, the ray we wants his closest point
     */
    private GeoPoint findClosestIntersection(Ray ray)throws Exception{
        List<GeoPoint> points = scene.get_geometries().findIntersections(ray);
        if(points == null)
            return null;
        GeoPoint closestPoint = points.get(0);
        for(int i = 1; i < points.size(); i++)
        {
            if(points.get(i).point.distance(ray.get_point()) < closestPoint.point.distance(ray.get_point()))
                closestPoint = points.get(i);
        }
        return closestPoint;
    }
}
