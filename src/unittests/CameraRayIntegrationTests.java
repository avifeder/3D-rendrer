package unittests;

import elements.Camera;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.assertEquals;
import geometries.Intersectable.GeoPoint;
/**
 * Testing Camera and ray intersections - integration
 * @author Daniel & Avi
 *
 */
public class CameraRayIntegrationTests {

    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     *  {@link geometries.Sphere#findIntersections(Ray)}
     *
     */
    @Test
    public void CameraRayIntegrationSphereTests() throws Exception {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Sphere sp = new Sphere(1.0, new Point3D(0, 0, 3));
        Ray ray;
        int sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = sp.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with sphere", 2, sumOfIntersections);

        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sp = new Sphere(2.5, new Point3D(0, 0, 2.5));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = sp.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;            }
        }
        assertEquals("Bad integration with sphere", 18, sumOfIntersections);

        camera = new Camera(new Point3D(0, 0, -0.5), new Vector(0, 0, 1), new Vector(0, -1, 0));
        sp = new Sphere(2, new Point3D(0, 0, 2));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = sp.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;            }
        }
        assertEquals("Bad integration with sphere", 10, sumOfIntersections);

        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        sp = new Sphere(10, new Point3D(0, 0, 1));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = sp.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with sphere", 9, sumOfIntersections);

        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        sp = new Sphere(0.5, new Point3D(0, 0, -1));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = sp.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with sphere", 0, sumOfIntersections);

    }
    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     *  {@link geometries.Plane#findIntersections(Ray)}
     *
     */
    @Test
    public void CameraRayIntegrationPlaneTests() throws Exception {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Plane plane = new Plane(new Point3D(0,0,3), new Vector(0,0,1));
        Ray ray;
        int sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = plane.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with plane", 9, sumOfIntersections);

        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        plane = new Plane(new Point3D(0,0,3), new Vector(0,0.01,1));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = plane.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with plane", 9, sumOfIntersections);

        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        plane = new Plane(new Point3D(0,0,1.5), new Vector(0,-1,1));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = plane.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with plane", 6, sumOfIntersections);
    }
    /**
     * Test method for
     * {@link Camera#constructRayThroughPixel(int, int, int, int, double, double, double)}.
     *  {@link geometries.Triangle#findIntersections(Ray)}
     *
     */
    @Test
    public void CameraRayIntegrationTriangleTests() throws Exception {
        Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        Triangle triangle = new Triangle(new Point3D(0,-1,2), new Point3D(1,1,2) , new Point3D(-1,1,2));
        Ray ray;
        int sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = triangle.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with triangle", 1, sumOfIntersections);

        camera = new Camera(Point3D.ZERO, new Vector(0, 0, 1), new Vector(0, -1, 0));
        triangle = new Triangle(new Point3D(0,-20,2), new Point3D(1,1,2) , new Point3D(-1,1,2));
        sumOfIntersections = 0;
        for(int i = 0; i<3; i++){
            for(int j = 0; j<3; j++)
            {
                ray = camera.constructRayThroughPixel(3, 3, j, i, 1, 3, 3);
                List intersections = triangle.findIntersections(ray);
                sumOfIntersections += intersections != null? intersections.size() : 0;
            }
        }
        assertEquals("Bad integration with triangle", 2, sumOfIntersections);



    }

}
