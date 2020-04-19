package unittests;

import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Geometries class
 * @author avi && daniel
 */
public class GeometriesTests {
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void findIntersections() throws Exception {
        // ============ Equivalence Partitions Tests ==============
        // TC01: some (not all) of the geometries is intersection (2 points)
        Sphere sphere = new Sphere(1, new Point3D(1, 0, 0));
        Plane plane = new Plane(new Point3D(1, 0, 0), new Vector(1, 0, 0));
        Triangle triangle = new Triangle(new Point3D(-1, 0, 0), new Point3D(3, 0, 0), new Point3D(0, 3, 0));
        Geometries geometries = new Geometries(sphere, plane, triangle);
        Ray ray = new Ray(new Point3D(-2, 0, 0), new Vector(3.72, 0, 0.7));
        assertEquals("Wrong number of points - some (not all) of the geometries is intersection", 3, geometries.findIntersections(ray).size());
        // =============== Boundary Values Tests ==================
        // TC02: all geometries are not intersection (0 points)
        ray = new Ray(new Point3D(-2, 0, 0), new Vector(0, 0, 1));
        assertEquals("Wrong number of points - all geometries are not intersection", null, geometries.findIntersections(ray));
        // TC03: one of the geometries is intersection (1 points)
        ray = new Ray(new Point3D(-3, 0, 0), new Vector(4, 0, 2));
        assertEquals("Wrong number of points - one of the geometries is intersection", 1, geometries.findIntersections(ray).size());
        // TC04: all geometries are intersection (4 points)
        ray = new Ray(new Point3D(-3, 3.4, -1.3), new Vector(3, -2, 1.3));
        assertEquals("Wrong number of points - all geometries are intersection", 4, geometries.findIntersections(ray).size());
        // TC05: no geometries
        geometries = new Geometries();
        assertEquals("Wrong number of points - no geometries", null, geometries.findIntersections(ray));


    }
}