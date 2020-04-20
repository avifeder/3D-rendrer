package unittests;

import org.junit.Test;
import primitives.Point3D;
import geometries.Tube;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Unit tests for geometries.Tube class
 * @author avi && daniel
 */
public class TubeTests {
    /**
     * Test method for {@link geometries.Tube#getNormal(Point3D)}
     */
    @Test
    public void getNormal() {
        try {
            // TC01: normal to Tube
            Tube t1 = new Tube(1, new Ray(new Point3D(0, 0, 1), new Vector(0, 0, 1)));
            assertEquals("Bad normal to Tube", new Vector(0,1,0), t1.getNormal(new Point3D(0,1,0)));
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link geometries.Geometries#findIntersections(primitives.Ray)}
     */
    @Test
    public void findIntersections() throws Exception {
        // ============ Equivalence Partitions Tests ==============

        // TC01: the ray intersection the tube twice (2 points)
        Tube tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        Ray ray = new Ray(new Point3D(2, 0, 2), new Vector(1, 0, -4));
        assertEquals("bad output - the ray intersection the tube twice", List.of(new Point3D(2.75, 0, -0.9999999999999996),new Point3D(2.25, 0, 1.0000000000000002)), tube.findIntersections(ray));

        // TC02: the ray intersection the tube one time (1 points)
        tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        ray = new Ray(new Point3D(2, 0, 0.5), new Vector(1, 0, -2.5));
        assertEquals("bad output - the ray intersection the tube one time", List.of(new Point3D(2.6, 0, -0.9999999999999996)), tube.findIntersections(ray));

        // TC03: the ray doesnt intersection the tube (0 points)
        tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        ray = new Ray(new Point3D(1, 0, 2), new Vector(1, 1, 1));
        assertEquals("bad output - the ray doesnt intersection the tube", null, tube.findIntersections(ray));
        // =============== Boundary Values Tests ==================

        // TC04: the ray is parallel to the tube (0 points)
        tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        ray = new Ray(new Point3D(2, 0, 0.5), new Vector(3, 0, 0));
        assertEquals("bad output - the ray is parallel to the tube", null, tube.findIntersections(ray));

        // TC04: the ray is orthogonal to the tube (0 points)
        tube = new Tube(1, new Ray(new Point3D(1, 0, 0), new Vector(1, 0, 0)));
        ray = new Ray(new Point3D(4, 0, -2), new Vector(0, 0, 4));
        assertEquals("bad output - the ray is orthogonal to the tube",  List.of(new Point3D(4, 0, 1),new Point3D(4, 0, -1)), tube.findIntersections(ray));

    }
}