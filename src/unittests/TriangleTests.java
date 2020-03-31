package unittests;

import geometries.Triangle;
import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;
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


}