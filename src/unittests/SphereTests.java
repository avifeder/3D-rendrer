package unittests;
import primitives.Point3D;
import primitives.Vector;

import geometries.Sphere;
import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Unit tests for geometries.Sphere class
 * @author avi && daniel
 */
public class SphereTests {
    /**
     * Test method for {@link geometries.Sphere#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        try {
            // TC01: normal to Sphere
            Sphere sp = new Sphere(1.0, new Point3D(1, 0, 0));
            assertEquals("Bad normal to sphere",new Vector(1, 0, 0), sp.getNormal(new Point3D(2, 0, 0)));
        }
        catch (Exception e){}
    }
}