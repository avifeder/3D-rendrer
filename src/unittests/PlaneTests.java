package unittests;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.*;

import java.util.List;

import static org.junit.Assert.*;
import geometries.Intersectable.GeoPoint;
/**
 * Unit tests for geometries.Plane class
 * @author avi && daniel
 */
public class PlaneTests {
    /**
     * Test method for {@link geometries.Plane#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        try {
            Point3D p1 = new Point3D(-2, 1, 0);
            Point3D p2 = new Point3D(3, 4, 2);
            Point3D p3 = new Point3D(1, -2, 5);
            Plane pl1 = new Plane(p1, p2, p3);
            Vector v1 = new Vector(p1.subtract(p2));
            Vector v2 = new Vector(p2.subtract(p3));
            assertEquals("Bad normal to plane",v1.crossProduct(v2).normalize(),pl1.getNormal(new Point3D(-2, 1, 0)));
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link geometries.Plane#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() throws Exception {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is intersects the plane (1 points)
        Plane plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        Ray ray = new Ray(new Point3D(0,0,1), new Vector(1,0,-1));
        assertEquals("Bad intersects to plane - line is intersects the plane", List.of(new GeoPoint(plane,new Point3D(1,0,0))) ,plane.findIntersections(ray));
        // TC02: Ray's line does not intersect the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(2,0,0), new Vector(1,0,1));
        assertEquals("Bad intersects to plane - line does not intersect the plane", null ,plane.findIntersections(ray));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray is parallel to the plane
        // TC03: Ray is parallel to the plane and included in the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(1,0,1), new Vector(0,0,1));
        assertEquals("Bad intersects to plane - Ray is parallel to the plane and included in the plane", null ,plane.findIntersections(ray));
        // TC04: Ray is parallel to the plane and not included in the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(2,0,0), new Vector(0,0,1));
        assertEquals("Bad intersects to plane - Ray is parallel to the plane and not included in the plane", null ,plane.findIntersections(ray));

        // **** Group: Ray is orthogonal to the plane
        // TC05:  Ray is orthogonal to the plane and 𝑃0 before the plane (1 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(-1,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to plane - Ray is orthogonal to the plane and p0 before the plane", List.of(new GeoPoint(plane,new Point3D(1,0,0))) ,plane.findIntersections(ray));
        // TC06:  Ray is orthogonal to the plane and 𝑃0  in the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(1,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to plane - Ray is orthogonal to the plane and p0 in the plane", null ,plane.findIntersections(ray));
        // TC07:  Ray is orthogonal to the plane and 𝑃0 after the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(2,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to plane - Ray is orthogonal to the plane and p0 after the plane", null ,plane.findIntersections(ray));

        // **** Group: Special cases
        // TC08: Ray is neither orthogonal nor parallel to and begins at the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(1,0,1), new Vector(1,0,1));
        assertEquals("Bad intersects to plane - Ray is neither orthogonal nor parallel to and begins at the plane", null ,plane.findIntersections(ray));
        // TC09: Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane (0 points)
        plane = new Plane(new Point3D(1,0,0), new Vector(1,0,0));
        ray = new Ray(new Point3D(1,0,0), new Vector(1,0,1));
        assertEquals("Bad intersects to plane - Ray is neither orthogonal nor parallel to the plane and begins in the same point which appears as reference point in the plane", null ,plane.findIntersections(ray));


    }
}