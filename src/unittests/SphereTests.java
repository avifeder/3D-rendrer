package unittests;
import primitives.*;
import geometries.*;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;
import geometries.Intersectable.GeoPoint;
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
    /**
     * Test method for {@link geometries.Sphere#findIntersections(Ray)}
     */
    @Test
    public void findIntersections() throws Exception {
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertEquals("Ray's line out of sphere", null,
                sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 1, 0))));

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<GeoPoint> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals("Wrong number of points -  Ray starts before and crosses the sphere", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere -  Ray starts before and crosses the sphere", List.of(new GeoPoint(sphere,p1), new GeoPoint(sphere,p2)), result);

        // TC03: Ray starts inside the sphere (1 point)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        Ray ray = new Ray(new Point3D(1.5,0,0), new Vector(0,0,1));
        assertEquals("Bad intersects to sphere -  Ray starts inside the sphere", List.of(new GeoPoint(sphere,new Point3D(1.5, 0, 0.8660254037844386))), sphere.findIntersections(ray));

        // TC04: Ray starts after the sphere (0 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(1.5,0,2), new Vector(0,0,1));
        assertEquals("Bad intersects to sphere -  Ray starts after the sphere", null, sphere.findIntersections(ray));

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC05: Ray starts at sphere and goes inside (1 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(2,0,0), new Vector(-1,0,1));
        assertEquals("Bad intersects to sphere -  Ray starts at sphere and goes inside", List.of(new GeoPoint(sphere,new Point3D(1, 0, 1))), sphere.findIntersections(ray));
        // TC06: Ray starts at sphere and goes outside (0 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(1,0,1), new Vector(-1,0,1));
        assertEquals("Bad intersects to sphere -  Ray starts at sphere and goes outside", null, sphere.findIntersections(ray));

        // **** Group: Ray's line goes through the center
        // TC07: Ray starts before the sphere (2 points)
        p1 = new Point3D(0, 0, 0);
        p2 = new Point3D(2, 0, 0);
        result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0), new Vector(1, 0, 0)));
        assertEquals("Wrong number of points - Ray starts before the sphere", 2, result.size());
        if (result.get(0).point.get_x().get() > result.get(1).point.get_x().get())
            result = List.of(result.get(1), result.get(0));
        assertEquals("Ray crosses sphere - Ray starts before the sphere", List.of(new GeoPoint(sphere,p1), new GeoPoint(sphere,p2)), result);

        // TC08: Ray starts at sphere and goes inside (1 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(0,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts at sphere and goes inside", List.of(new GeoPoint(sphere,new Point3D(2, 0, 0))), sphere.findIntersections(ray));

        // TC09: Ray starts inside (1 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(1.5,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to sphere - Ray starts inside", List.of(new GeoPoint(sphere,new Point3D(2, 0, 0))), sphere.findIntersections(ray));

        // TC10: Ray starts at the center (1 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(1,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts at the center", List.of(new GeoPoint(sphere,new Point3D(2, 0, 0))), sphere.findIntersections(ray));

        // TC11: Ray starts at sphere and goes outside (0 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(2,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts at sphere and goes outside", null, sphere.findIntersections(ray));

        // TC12: Ray starts after sphere (0 points)
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(3,0,0), new Vector(1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts after sphere", null, sphere.findIntersections(ray));

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)

        // TC13: Ray starts before the tangent point
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(2,0,1), new Vector(-1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts before the tangent point", null, sphere.findIntersections(ray));

        // TC14: Ray starts at the tangent point
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(1,0,1), new Vector(-1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts at the tangent point", null, sphere.findIntersections(ray));

        // TC15: Ray starts after the tangent point
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(0,0,1), new Vector(-1,0,0));
        assertEquals("Bad intersects to sphere -  Ray starts after the tangent point", null, sphere.findIntersections(ray));

        // **** Group: Special cases

        // TC16: Ray's line is outside, ray is orthogonal to ray start to sphere's center line
        sphere = new Sphere(1d, new Point3D(1, 0, 0));
        ray = new Ray(new Point3D(3,0,0), new Vector(0,0,1));
        assertEquals("Bad intersects to sphere -  ray is orthogonal to ray start to sphere's center line", null, sphere.findIntersections(ray));


    }
}