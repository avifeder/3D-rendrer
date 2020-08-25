/**
 *
 */
package unittests;

import static org.junit.Assert.*;

import org.junit.Test;

import geometries.*;
import primitives.*;

import java.util.List;
import geometries.Intersectable.GeoPoint;
/**
 * Testing Polygons
 * @author Dan
 *
 */
public class PolygonTests {

    /**
     * Test method for
     * {@link geometries.Polygon# Polygon(primitives.Point3D, primitives.Point3D, primitives.Point3D, primitives.Point3D)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(-1, 1, 1));
        } catch (Exception e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(0, 1, 0),
                    new Point3D(1, 0, 0), new Point3D(-1, 1, 1));
            fail("Constructed a polygon with wrong order of vertices");
        } catch (Exception e) {}

        // TC03: Not in the same plane
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 2, 2));
            fail("Constructed a polygon with vertices that are not in the same plane");
        } catch (Exception e) {}

        // TC04: Concave quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0.5, 0.25, 0.5));
            fail("Constructed a concave polygon");
        } catch (Exception e) {}

        // =============== Boundary Values Tests ==================

        // TC10: Vertix on a side of a quadrangular
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0.5, 0.5));
            fail("Constructed a polygon with vertix on a side");
        } catch (Exception e) {}

        // TC11: Last point = first point
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 0, 1));
            fail("Constructed a polygon with vertice on a side");
        } catch (Exception e) {}

        // TC12: Collocated points
        try {
            new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0),
                    new Point3D(0, 1, 0), new Point3D(0, 1, 0));
            fail("Constructed a polygon with vertice on a side");
        } catch (Exception e) {}

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point3D)}.
     */
    @Test
    public void testGetNormal() throws Exception {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here
        try {
            Polygon pl = new Polygon(new Point3D(0, 0, 1), new Point3D(1, 0, 0), new Point3D(0, 1, 0),
                    new Point3D(-1, 1, 1));
            double sqrt3 = Math.sqrt(1d / 3);
            assertEquals("Bad normal to trinagle", new Vector(sqrt3, sqrt3, sqrt3), pl.getNormal(new Point3D(0, 0, 1)));
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link geometries.Polygon#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() throws Exception {

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is Inside polygon (1 points)
        Polygon polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        Ray ray = new Ray(new Point3D(0,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is Inside polygon", List.of(new GeoPoint(polygon, new Point3D(0,0.5,0))) ,polygon.findIntersections(ray));
        // TC01: Ray's line is Outside against edge (0 points)
        polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0.5,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is Outside against edge", null ,polygon.findIntersections(ray));
        // TC01:  Ray's line is Outside against vertex (0 points)
        polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,2,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is Outside against vertex", null ,polygon.findIntersections(ray));

        // =============== Boundary Values Tests ==================

        // TC01: Ray's line is On edge (0 points)
        polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0.5,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is On edge", null ,polygon.findIntersections(ray));
        // TC01: Ray's line is In vertex (0 points)
        polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(0,1,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is In vertex", null ,polygon.findIntersections(ray));
        // TC01: Ray's line is On edge's continuation (0 points)
        polygon = new Polygon(new Point3D(-1,0,0), new Point3D(1,0,0) , new Point3D(0,1,0));
        ray = new Ray(new Point3D(2,0,-1), new Vector(0,0,1));
        assertEquals("Bad intersects to polygon - line is On edge's continuation", null ,polygon.findIntersections(ray));


    }


}
