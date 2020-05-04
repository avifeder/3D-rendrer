package renderer;

import elements.Camera;
import geometries.Intersectable;
import geometries.RadialGeometry;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.awt.*;
import java.util.List;


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
                    List<Point3D> intersectionPoints = geometries.findIntersections(ray);
                    if(intersectionPoints == null)
                        imageWriter.writePixel(x, y, background);
                    else {
                        Point3D closestPoint = getClosestPoint(intersectionPoints);
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
     * calcColor - return the intensity int he point
     * @param p no meaning for now
     */
    public primitives.Color calcColor(Point3D p){
        return scene.get_ambientLight().getIntensity();
    }

    /**
     * getClosestPoint - calculate the closest intersection point
     * @param points list of Point3D intersection point's
     * @return the closest intersection point
     */
    public Point3D getClosestPoint(List<Point3D> points){
        if(points == null)
            return null;
        Point3D locationOfCamera = scene.get_camera().getLocation();
        Point3D closestPoint = points.get(0);
        for(int i = 1; i < points.size(); i++)
        {
            if(points.get(i).distance(locationOfCamera) < closestPoint.distance(locationOfCamera))
                closestPoint = points.get(i);
        }
        return closestPoint;
    }
}
