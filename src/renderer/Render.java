package renderer;

import elements.*;
import geometries.Intersectable;
import primitives.*;
import scene.Scene;
import java.awt.Color;
import java.util.LinkedList;
import java.util.List;


import geometries.Intersectable.GeoPoint;

import static primitives.Util.*;

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

    /**
     * numOfRaysForSuperSampling - number of rays for super sampling
     */
    private int maxRaysForSuperSampling = 100;

    /**
     * Multiple reflection and refraction rays
     * distanceOfGrid - the distance between the object and the grid for reflection and refraction
     * numOfRaysForDiffusedAndGlossy - number of rays for reflection and refraction
     */
    private double distanceForDiffusedAndGlossy = 100;
    private int numOfRaysForDiffusedAndGlossy = 1;
    private boolean AdaptiveSuperSamplingFlag = true;




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
                synchronized(System.out){System.out.printf("\r %02d%%", percent);};
            }
            if(percent >= 0)
                return true;
            if(Render.this._print){
                synchronized(System.out){System.out.printf("\r %02d%%", 100);};
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

                            if (AdaptiveSuperSamplingFlag)
                                {
                                primitives.Color color = AdaptiveSuperSampling(nX, nY, pixel.col, pixel.row, distance, width, height, maxRaysForSuperSampling);
                                imageWriter.writePixel(pixel.col, pixel.row, color.getColor());
                                }
                            else
                                {
                                rays = camera.constructRaysThroughPixel(nX, nY, pixel.col, pixel.row, distance, width, height, maxRaysForSuperSampling);
                                imageWriter.writePixel(pixel.col, pixel.row, calcColor(rays).getColor());
                                }
                        }
                            catch (Exception e) {}
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
    public void setMaxRaysForSuperSampling(int maxRaysForSuperSampling) {
        this.maxRaysForSuperSampling = maxRaysForSuperSampling;
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

                List<Ray> reflectedRays = constructReflectedRays(n, p.point,ray, material.get_DiffusedAndGlossy());
                primitives.Color tempColor1 = primitives.Color.BLACK;
                //calculate for each ray
                for(Ray reflectedRay: reflectedRays)
                {
                    GeoPoint gp = findClosestIntersection(reflectedRay);
                    tempColor1 = tempColor1.add(gp == null ? primitives.Color.BLACK : calcColor(gp, reflectedRay, level - 1, kkr).scale(kr));
                }
                color = color.add(tempColor1.reduce(reflectedRays.size()));

            }

            //transparency calculate
            double kt = material.get_KT();
            double kkt = k*kt;
            if(kkt > MIN_CALC_COLOR_K) {
                List<Ray> refractedRays = constructRefractedRays(n, p.point,ray, material.get_DiffusedAndGlossy());
                primitives.Color tempColor2 = primitives.Color.BLACK;
                //calculate for each ray
                for(Ray refractedRay: refractedRays)
                {
                    GeoPoint gp = findClosestIntersection(refractedRay);
                    tempColor2 = tempColor2.add(gp == null? primitives.Color.BLACK : calcColor(gp, refractedRay, level -1, kkt).scale(kt));
                }
                color = color.add(tempColor2.reduce(refractedRays.size()));
                //color = color.add(gp == null? primitives.Color.BLACK : calcColor(gp, refractedRay, level -1, kkt).scale(kt));
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

    private primitives.Color calcColor(Ray ray) throws Exception {
        GeoPoint gp;
            gp = findClosestIntersection(ray);
            if(gp == null)
                return this.scene.get_background();
            else
                return calcColor(gp, ray);
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
     * unshaded - check if an intersection point need to be shadow or not
     * @param l direction from light to point
     * @param n normal to the point
     * @param gp the point we check for shadow
     * @param lightSource the light source
     * @return true if its an intersection point need to be unshaded
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
    private List<Ray> constructReflectedRays(Vector n, Point3D point, Ray ray, double DiffusedAndGlossy) throws Exception
    {
        double nv = ray.get_vector().dotProduct(n);
        Vector v = ray.get_vector();
        Vector ref = v.add(n.scale(-2*nv));
        return RaysOfGrid(n, point, ref, -1, DiffusedAndGlossy);
    }

    /**
     * contract Refracted Ray
     * @param n normal to point
     * @param point, the intersection point
     * @param ray, the ray we wants his refracted ray
     */
    private List<Ray> constructRefractedRays(Vector n, Point3D point, Ray ray, double DiffusedAndGlossy) throws Exception
    {
        return RaysOfGrid(n, point, ray.get_vector(), 1, DiffusedAndGlossy);
    }

    /**
     * calculate the rays that cross the grid of refraction and reflection
     * @param n normal to point
     * @param point the intersection point
     * @param Vto the direction of the main ray
     * @param direction 1 for refraction, -1 for reflection
     * @return list of rays that cross the grid
     */
    private List<Ray> RaysOfGrid(Vector n, Point3D point, Vector Vto, int direction, double DiffusedAndGlossy) throws Exception {
        if(direction != 1 && direction != -1)
            throw new IllegalArgumentException("direction must be 1 or -1");
        double gridSize = DiffusedAndGlossy;
        int numOfRowCol = isZero(gridSize)? 1: (int)Math.ceil(Math.sqrt(numOfRaysForDiffusedAndGlossy));
        Vector Vup = Vto.findRandomOrthogonal();//vector in the grid
        Vector Vright = Vto.crossProduct(Vup);//vector in the grid
        Point3D centerOfGrid = point.add(Vto.scale(distanceForDiffusedAndGlossy)); // center point of the grid
        double sizeOfCube = gridSize/numOfRowCol;//size of each cube in the grid
        List rays = new LinkedList<Ray>();
        n = n.dotProduct(Vto) > 0 ? n.scale(-direction) : n.scale(direction);//fix the normal direction
        Point3D tempcenterOfGrid = centerOfGrid;//save the center of the grid
        Vector tempRayVector;
        for (int row = 0; row < numOfRowCol; row++){
            double xAsixChange= (row - (numOfRowCol/2d))*sizeOfCube + sizeOfCube/2d;
            for(int col = 0; col < numOfRowCol; col++)
            {
                double yAsixChange= (col - (numOfRowCol/2d))*sizeOfCube + sizeOfCube/2d;
                if(xAsixChange != 0) centerOfGrid = centerOfGrid.add(Vright.scale(-xAsixChange)) ;
                if(yAsixChange != 0) centerOfGrid = centerOfGrid.add(Vup.scale(-yAsixChange)) ;
                tempRayVector = centerOfGrid.subtract(point);
                if(n.dotProduct(tempRayVector) < 0 && direction == 1) //refraction
                    rays.add(new Ray(point, tempRayVector, n));
                if(n.dotProduct(tempRayVector) > 0 && direction == -1) //reflection
                    rays.add(new Ray(point, tempRayVector, n));
                centerOfGrid = tempcenterOfGrid;
            }
        }


        return rays;
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

    /**
     * set distance of grid
     * set the distance between the object and the grid for reflection and refraction
     */
    public void setDistanceForDiffusedAndGlossy(double distanceForDiffusedAndGlossy) {
        this.distanceForDiffusedAndGlossy = distanceForDiffusedAndGlossy;
    }

    /**
     * set num of rays
     * set the number of rays for reflection and refraction
     */
    public void setNumOfRaysForDiffusedAndGlossy(int numOfRaysForDiffusedAndGlossy) {
        this.numOfRaysForDiffusedAndGlossy = numOfRaysForDiffusedAndGlossy;
    }



    public primitives.Color AdaptiveSuperSampling(int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, int numOfRays) throws Exception {
        Camera camera = scene.get_camera();
        Vector Vright = camera.getVright();
        Vector Vup = camera.getVup();
        Point3D cameraLoc = camera.getLocation();
        int numOfRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        if(numOfRaysInRowCol == 1)
            return calcColor(camera.constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,screenHeight));
        Point3D Pc;
        if (screenDistance != 0)
            Pc = cameraLoc.add(camera.getVto().scale(screenDistance));
        else
            Pc = cameraLoc;
        Point3D Pij = Pc;
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;
        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;
        if(Xj != 0) Pij = Pij.add(Vright.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(Vup.scale(-Yi)) ;
        double PRy = Ry/numOfRaysInRowCol;
        double PRx = Rx/numOfRaysInRowCol;
        return AdaptiveSuperSamplingRec(Pij, Rx, Ry, PRx, PRy,cameraLoc,Vright, Vup);
    }


    private primitives.Color AdaptiveSuperSamplingRec(Point3D centerP, double Width, double Height, double minWidth, double minHeight, Point3D cameraLoc,Vector Vright,Vector Vup) throws Exception {

        Point3D corner1 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(-Height / 2)),
                corner2 = centerP.add(Vright.scale(Width / 2)).add(Vup.scale(Height / 2)),
                corner3 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(-Height / 2)),
                corner4 = centerP.add(Vright.scale(-Width / 2)).add(Vup.scale(Height / 2));

        Ray     ray1 = new Ray(cameraLoc, corner1.subtract(cameraLoc)),
                ray2 = new Ray(cameraLoc, corner2.subtract(cameraLoc)),
                ray3 = new Ray(cameraLoc, corner3.subtract(cameraLoc)),
                ray4 = new Ray(cameraLoc, corner4.subtract(cameraLoc));

        primitives.Color color1= calcColor(ray1),
                         color2= calcColor(ray2),
                         color3= calcColor(ray3),
                         color4= calcColor(ray4);

        if(Width<=minWidth || Height<=minHeight)
            return color1.add(color2, color3, color4).reduce(4);


        if (isAlmostEquals(color1,color2)&& isAlmostEquals(color1,color3)&& isAlmostEquals(color1,color4))
            return color1;

        Point3D centerP1 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(-Height / 4)),
                centerP2 = centerP.add(Vright.scale(Width / 4)).add(Vup.scale(Height / 4)),
                centerP3 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(-Height / 4)),
                centerP4 = centerP.add(Vright.scale(-Width / 4)).add(Vup.scale(Height / 4));



        return AdaptiveSuperSamplingRec(centerP1, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup).add(
               AdaptiveSuperSamplingRec(centerP2, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
               AdaptiveSuperSamplingRec(centerP3, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup),
               AdaptiveSuperSamplingRec(centerP4, Width/2,  Height/2,  minWidth,  minHeight ,  cameraLoc, Vright, Vup)
        ).reduce(4);

    }

    public void setAdaptiveSuperSamplingFlag(boolean adaptiveSuperSamplingFlag) {
        AdaptiveSuperSamplingFlag = adaptiveSuperSamplingFlag;
    }

}
