//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Vector;


/**
 * Sphere class extends RadialGeometry
 * represents geometry Sphere
 * @author avi && daniel
 */
public class Sphere extends RadialGeometry {

    /**
     * contractor - gets double radius
     * @param radius of the Sphere
     */
    public Sphere(double radius) {
        super(radius);
    }

    @Override
    public Vector getNormal(Point3D point) {
        return null;
    }
}
