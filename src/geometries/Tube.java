//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Vector;

/**
 * Tube class extends RadialGeometry
 * represents geometry Tube
 * @author avi && daniel
 */
public class Tube extends RadialGeometry {

    /**
     * contractor - gets double radius
     * @param radius of the Tube
     */
    public Tube(double radius) {
        super(radius);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
