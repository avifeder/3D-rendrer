//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Vector;


/**
 * Cylinder class represents cylinder
 * @author avi && daniel
 */
public class Cylinder extends RadialGeometry {

    /**
     * @param radius double
     */
    public Cylinder(double radius) {
        super(radius);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
