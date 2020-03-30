//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

/**
 * Geometry interface represents normal in geometry shapes
 * @author avi && daniel
 */
public interface Geometry {
    /**
     * @param point Point3D
     * @return Vector - the normal vector
     */
    public Vector getNormal(Point3D point);
}
