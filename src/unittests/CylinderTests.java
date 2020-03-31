package unittests;

import geometries.Cylinder;
import org.junit.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Cylinder class
 * @author avi && daniel
 */
public class CylinderTests {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Cylinder c1 = new Cylinder(1, new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)), 4);
            Point3D p1 = new Point3D(0,0,5);
            // TC01: point on the up base
            assertEquals("Bad normal to Cylinder - point on the up base",new Vector(0,0,1),c1.getNormal(p1));
            p1 = new Point3D(0,0.5,1);
            // TC02: point on the down base
            assertEquals("Bad normal to Cylinder - point on the down base",new Vector(0,0,1),c1.getNormal(p1));
            p1 = new Point3D(1,0,2);
            // TC03: point on the side
            assertEquals("Bad normal to Cylinder - point on the side",new Vector(1,0,0),c1.getNormal(p1));
            // =============== Boundary Values Tests ==================
            p1 = new Point3D(1,0,5);
            // TC04: point on the up base and side
            assertEquals("Bad normal to Cylinder - point on the up base and side",new Vector(1,0,0),c1.getNormal(p1));
            p1 = new Point3D(0,1,1);
            // TC05: point on the down base and side
            assertEquals("Bad normal to Cylinder - point on the down base and side",new Vector(0,1,0),c1.getNormal(p1));
        }
        catch (Exception e)
        {}
    }
}