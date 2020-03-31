package unittests;

import org.junit.Test;
import primitives.Point3D;
import geometries.Tube;
import primitives.Ray;
import primitives.Vector;

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
}