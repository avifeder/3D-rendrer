package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.Assert.*;

/**
 * Unit tests for primitives.Vector class
 * @author avi && daniel
 */
public class VectorTests {
    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void subtract() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            assertTrue("Vector - Vector does not work correctly",new Vector(1, 1, 1).equals(new Vector(2, 3, 4).subtract(v1)));
            v1 = new Vector(-1, -2, -3);
            assertTrue("Vector - Vector does not work correctly",new Vector(3, 5, 7).equals(new Vector(2, 3, 4).subtract(v1)));
            // =============== Boundary Values Tests ==================
            new Vector(1, 2, 3).subtract(new Vector(1, 2, 3));
            fail("subtract doesn't throws exeption");
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#add(Vector)}.
     */
    @Test
    public void add() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            assertTrue("Vector + Point does not work correctly",new Vector(5,7,9).equals(new Vector(4,5,6).add(v1)));
            assertTrue("Vector + Point does not work correctly",new Vector(-3,-3,-3).equals(new Vector(-4,-5,-6).add(v1)));
            v1 = new Vector(-1, -2,-3);
            assertTrue("Vector + Point does not work correctly",new Vector(-5,-7,-9).equals(new Vector(-4,-5,-6).add(v1)));
            // =============== Boundary Values Tests ==================
            new Vector(1, 2, 3).add(new Vector(-1, -2, -3));
            fail("add doesn't throws exeption");
        }
        catch (Exception e){}
    }

    @Test
    public void scale() {
    }

    @Test
    public void dotProduct() {
    }

    @Test
    public void crossProduct() {
    }

    @Test
    public void lengthSquared() {
    }

    @Test
    public void length() {
    }

    @Test
    public void normalize() {
    }

    @Test
    public void normalized() {
    }
}