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

    Point3D _center;
    /**
     * contractor - gets double radius
     * @param radius of the Sphere
     */
    public Sphere(double radius, Point3D p) {
        super(radius);
        _center = p;
    }

    public Point3D get_center() {
        return _center;
    }

    @Override
    public String toString() {
        return "Sphere{" +" Radius =" + _radius +
                " , center=" + _center +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        try {
            return point.subtract(_center).normalize();
        }
        catch (Exception e){
            return null;}
    }
}
