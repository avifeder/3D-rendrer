package unittests;

import org.junit.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.Collections;

import static org.junit.Assert.*;


/**
 * Unit tests for primitives.Vector class
 * @author avi && daniel
 */
public class VectorTests {

    /**
     * Test method for {@link primitives.Vector#Vector(double, double, double)}
     */
    @Test
    public void testConstructor() {
        try{
            // TC01: zero vector
            new Vector(0, 0, 0);
            fail("ERROR: zero vector does not throw an exception");
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#subtract(primitives.Vector)}.
     */
    @Test
    public void subtract() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            // TC01: Positive Vector - Positive Vector
            assertEquals("Positive Vector - Positive Vector does not work correctly",new Vector(1, 1, 1),new Vector(2, 3, 4).subtract(v1));
            // TC02: Negative Vector - Positive Vector
            assertEquals("Negative Vector - Positive Vector does not work correctly",new Vector(-3, -5, -7),new Vector(-2, -3,- 4).subtract(v1));
            v1 = new Vector(-1, -2, -3);
            // TC03: Positive Vector - Negative Vector
            assertEquals("Positive Vector - Negative Vector does not work correctly",new Vector(3, 5, 7),new Vector(2, 3, 4).subtract(v1));
            // TC04: Negative Vector - Negative Vector
            assertEquals("Negative Vector - Negative Vector does not work correctly",new Vector(-1, -1, -1), new Vector(-2, -3,- 4).subtract(v1));

            // =============== Boundary Values Tests ==================
            // TC05: subtract to zero
            new Vector(1, 2, 3).subtract(new Vector(1, 2, 3));
            fail("subtract to zero doesn't throws exeption");
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
            // TC01: Positive Vector + Positive Point
            assertEquals("Positive Vector + Positive Point does not work correctly",new Vector(5,7,9),new Vector(4,5,6).add(v1));
            // TC02:Negative Vector + Positive Point
            assertEquals("Negative Vector + Positive Point does not work correctly",new Vector(-3,-3,-3),new Vector(-4,-5,-6).add(v1));
            v1 = new Vector(-1, -2,-3);
            // TC03:Negative Vector + Negative Point
            assertEquals("Negative Vector + Negative Point does not work correctly",new Vector(-5,-7,-9),new Vector(-4,-5,-6).add(v1));
            // TC04:Positive Vector + Negative Point
            assertEquals("Positive Vector + Negative Point does not work correctly",new Vector(3,3,3),new Vector(4,5,6).add(v1));

            // =============== Boundary Values Tests ==================
            // TC05:add to zero
            new Vector(1, 2, 3).add(new Vector(-1, -2, -3));
            fail("add to zero doesn't throws exeption");
        }
        catch (Exception e){}
    }

    /**
     * Test method for {@link primitives.Vector#scale(double)}.
     */
    @Test
    public void scale() {
        try {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        // TC01:Positive scale on Positive vector
        assertEquals("Positive scale on Positive vector does not work correctly", v1.scale(5),new Vector(5,10,15));
        // TC02:Negative scale on Negative vector
        assertEquals("Negative scale on Negative vector does not work correctly", v1.scale(-5),new Vector(-5,-10,-15));
        v1 = new Vector(-1, -2, -3);
        // TC03:Negative scale on Positive vector
        assertEquals("Negative scale on Positive vector does not work correctly", v1.scale(-5),new Vector(5,10,15));
        // TC04:Positive scale on Negative vector
        assertEquals("Positive scale on Negative vector does not work correctly", v1.scale(5),new Vector(-5,-10,-15));

        // =============== Boundary Values Tests ==================
            // TC05:scale to zero
        v1.scale(0);
        fail("scale to zero doesn't throws exeption");
        }
        catch (Exception e){}
    }

    /**
     * Test method for {@link primitives.Vector#dotProduct(Vector)}.
     */
    @Test
    public void dotProduct() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            // TC01:Positive vector dotProduct on Positive vector
            assertEquals("Positive vector dotProduct on Positive vector does not work correctly",6, v1.dotProduct(new Vector(1,1,1)), 0.0001);
            // TC02:positive vector dotProduct on Negative
            assertEquals("Positive vector dotProduct on Negative vector does not work correctly",-6, v1.dotProduct(new Vector(-1,-1,-1)), 0.0001);
            v1 = new Vector(-1, -2, -3);
            // TC03:Negative vector dotProduct on Positive vector
            assertEquals("Negative vector dotProduct on Positive vector does not work correctly",-6, v1.dotProduct(new Vector(1,1,1)), 0.0001);
            // TC04:Negative vector dotProduct on Negative vector
            assertEquals("Negative vector dotProduct on Negative vector does not work correctly",6, v1.dotProduct(new Vector(-1,-1,-1)), 0.0001);
            // =============== Boundary Values Tests ==================
            // TC05:dotProduct of orthogonal vectors
            assertEquals("dotProduct of orthogonal vectors does not work correctly",0, v1.dotProduct(new Vector(-2,1,0)), 0.0001);
            // TC06:dotProduct of parallel vectors
            assertEquals("dotProduct of parallel vectors does not work correctly",28, v1.dotProduct(new Vector(-2,-4,-6)), 0.0001);
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#crossProduct(Vector)}.
     */
    @Test
    public void crossProduct() {
        try {
        // ============ Equivalence Partitions Tests ==============
        Vector v1 = new Vector(1, 2, 3);
        // TC01:Positive vector crossProduct on Positive vector
        assertEquals("Positive vector crossProduct on Positive vector does not work correctly",new Vector(-1, 2, -1), v1.crossProduct(new Vector(1,1,1)));
        // TC02:Positive vector crossProduct on Negative vector
        assertEquals("Positive vector crossProduct on Negative vector does not work correctly",new Vector(1, -2, 1), v1.crossProduct(new Vector(-1,-1,-1)));
        v1 = new Vector(-1, -2, -3);
        // TC3:Negative vector crossProduct on Positive vector
        assertEquals("Negative vector crossProduct on Positive vector does not work correctly",new Vector(1, -2, 1), v1.crossProduct(new Vector(1,1,1)));
        // TC04:Negative vector crossProduct on Negative vector
        assertEquals("Negative vector crossProduct on Negative vector does not work correctly",new Vector(-1, 2, -1), v1.crossProduct(new Vector(-1,-1,-1)));
        // =============== Boundary Values Tests ==================
        v1 = new Vector(2, -2, 0);
        // TC05:crossProduct of orthogonal vectors
        assertEquals("crossProduct of orthogonal vectors does not work correctly",new Vector(-2,-2,4), v1.crossProduct(new Vector(1,1,1)));
        // TC06:crossProduct to zero
        v1.crossProduct(new Vector(4, -4, 0));
        fail("crossProduct to zero doesn't throws exeption");
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#lengthSquared()}.
     */
    @Test
    public void lengthSquared() {
        try {
        // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(1, 2, 3);
            // TC01:lengthSquared for positive vector
            assertEquals("lengthSquared for positive vector does not work correctly",14, v1.lengthSquared(),0.0001);
            v1 = new Vector(-1, -2, -3);
            // TC02:lengthSquared for negative vector
            assertEquals("lengthSquared for negative vector does not work correctly",14, v1.lengthSquared(),0.0001);
            // =============== Boundary Values Tests ==================
            // none
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#length()}.
     */
    @Test
    public void length() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(3, 4, 0);
            // TC01:length for positive vector
            assertEquals("length for positive vector does not work correctly",5, v1.length(),0.0001);
            v1 = new Vector(-3, -4, 0);
            // TC02:length for negative vector
            assertEquals("length for negative vector does not work correctly",5, v1.length(),0.0001);
            // =============== Boundary Values Tests ==================
            // none
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#normalize()}.
     */
    @Test
    public void normalize() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(3, 4, 0);
            // TC01:normalize for positive vector
            assertEquals("normalize for positive vector does not work correctly",new Vector((double)3/5,(double)4/5,0), v1.normalize());
            v1 = new Vector(-3, -4, 0);
            // TC02:normalize for negative vector
            assertEquals("normalize for negative vector does not work correctly",new Vector((double)-3/5,(double)-4/5,0), v1.normalize());
            // TC03:creates a new vector
            v1 = new Vector(3, 4, 0);
            Vector v2 = v1.normalize();
            assertTrue("normalize() function creates a new vector",v2==v1);
            // =============== Boundary Values Tests ==================
            // none
        }
        catch (Exception e){}
    }
    /**
     * Test method for {@link primitives.Vector#normalized()}.
     */
    @Test
    public void normalized() {
        try {
            // ============ Equivalence Partitions Tests ==============
            Vector v1 = new Vector(3, 4, 0);
            // TC01:normalized for positive vector
            assertEquals("normalized for positive vector does not work correctly",new Vector((double)3/5,(double)4/5,0),v1.normalized());
            v1 = new Vector(-3, -4, 0);
            // TC02:normalized for negative vector
            assertEquals("normalized for negative vector does not work correctly",new Vector((double)-3/5,(double)-4/5,0), v1.normalized());
            // TC03:not creates a new vector
            v1 = new Vector(3, 4, 0);
            Vector v2 = v1.normalized();
            assertFalse("normalized() function not creates a new vector",v2==v1);
            // =============== Boundary Values Tests ==================
            // none
        }
        catch (Exception e){}
    }
}