package unittests;

import geometries.Plane;
import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
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
}