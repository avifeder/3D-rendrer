package unittests;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;
import geometries.Intersectable.GeoPoint;
/**
 * Unit tests for geometries.Triangle class
 * @author avi && daniel
 */
public class TriangleTests {

    /**
     * Test method for {@link geometries.Triangle#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        try {
            // TC01: normal to trinagle
            Point3D p1 = new Point3D(-2, 1, 0);
            Point3D p2 = new Point3D(3, 4, 2);
            Point3D p3 = new Point3D(1, -2, 5);
            Triangle t1 = new Triangle(p1, p2, p3);
            Vector v1 = new Vector(p1.subtract(p2));
            Vector v2 = new Vector(p2.subtract(p3));
            assertEquals("Bad normal to trinagle",v1.crossProduct(v2).normalize(),t1.getNormal(new Point3D(-2, 1, 0)));
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link geometries.Triangle#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() throws Exception {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is Inside triangle (1 points)
        Triangle triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        Ray ray = new Ray(new Point3D(0,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Inside triangle", List.of(new GeoPoint(triangle,new Point3D(0,0.5,0))) ,triangle.findIntersections(ray));
        // TC01: Ray's line is Outside against edge (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Outside against edge", null ,triangle.findIntersections(ray));
        // TC01:  Ray's line is Outside against vertex (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,2,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is Outside against vertex", null ,triangle.findIntersections(ray));

        // =============== Boundary Values Tests ==================

        // TC01: Ray's line is On edge (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0.5,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is On edge", null ,triangle.findIntersections(ray));
        // TC01: Ray's line is In vertex (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,1,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is In vertex", null ,triangle.findIntersections(ray));
        // TC01: Ray's line is On edge's continuation (0 points)
        triangle = new Triangle(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to triangle - line is On edge's continuation", null ,triangle.findIntersections(ray));


    }

}