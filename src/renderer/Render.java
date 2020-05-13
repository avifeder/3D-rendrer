package renderer;

import elements.Camera;
import elements.LightSource;
import geometries.Intersectable;
import geometries.RadialGeometry;
import primitives.Material;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.awt.*;
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
     * contractor - gets gets the image params
     * @param imageWriter the D2 image
     * @param scene the scene to converts
     */
    public Render(ImageWriter imageWriter, Scene scene) {
        this.scene = scene;
        this.imageWriter = imageWriter;
    }

    /**
     * Render - Image
     * Converts the image from D3 to D2
     * The result is written in imageWriter
     */
    public void renderImage() {
        Camera camera = scene.get_camera();
        Intersectable geometries = scene.get_geometries();
        Color background = scene.get_background().getColor();
        double distance = scene.get_distance();
        int nX = imageWriter.getNx();
        int nY = imageWriter.getNy();
        double width = imageWriter.getWidth();
        double height = imageWriter.getHeight();

        for(int x = 0; x < nX; x++) {
            for(int y = 0; y < nY; y++) {
                try {
                    Ray ray = camera.constructRayThroughPixel(nX, nY, x, y, distance, width, height);
                    List<GeoPoint> intersectionPoints = geometries.findIntersections(ray);
                    if(intersectionPoints == null)
                        imageWriter.writePixel(x, y, background);
                    else {
                        GeoPoint closestPoint = getClosestPoint(intersectionPoints);
                        imageWriter.writePixel(x, y, calcColor(closestPoint).getColor());
                    }
                }
                catch (Exception e){}
            }
        }
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
    private primitives.Color calcColor(GeoPoint p){
        try {
            primitives.Color color = scene.get_ambientLight().get_intensity();
            color = color.add(p.geometry.get_emmission());
            Vector v = p.point.subtract(scene.get_camera().getLocation()).normalize();
            Vector n = p.geometry.getNormal(p.point);
            Material material = p.geometry.get_material();
            int nShininess = material.get_nShininess();
            double kd = material.get_kD();
            double ks = material.get_kS();
            for (LightSource lightSource : scene.get_lights()){
                Vector l = lightSource.getL(p.point);
                double nl = alignZero(n.dotProduct(l));
                double nv = alignZero(n.dotProduct(v));
                if((nl>0 && nv>0) || (nl<0 && nv<0))
                {
                    primitives.Color ip =lightSource.getIntensity(p.point);
                    color = color.add(calcDiffusive(kd, nl, ip), calcSpecular(ks, l, n, nl, v, nShininess, ip));
                }
            }
            return color;
        }
        catch (Exception e)
        {
            return scene.get_background();
        }
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
}
