//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/**
 * Tube class extends RadialGeometry
 * represents geometry Tube
 * @author avi && daniel
 */
public class Tube extends RadialGeometry {

    Ray _axisRay;

    public Ray get_axisRay() {
        return _axisRay;
    }

    /**
     * contractor - gets double radius
     * @param radius of the Tube
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        _axisRay = ray;
    }

    /**
     * getNormal - gets the normal of the tube
     * @param point of the Tube
     */
    @Override
    public Vector getNormal(Point3D point)
    {
        try {
            Vector v1 = new Vector(point.subtract(_axisRay.get_point()));
            Vector v2 = new Vector(_axisRay.get_vector().scale(_axisRay.get_vector().dotProduct(v1)));
            return v1.subtract(v2).normalize();
        }
        catch (Exception e){
            return null;}
    }

    @Override
    public String toString() {
        return "Tube{" +
                "Ray =" + _axisRay + ", Radius =" + _radius +
                '}';
    }
}
