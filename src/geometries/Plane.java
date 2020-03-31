//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Vector;


/**
 * Plane class implements Geometry
 * represents plane by vector and point
 * @author avi && daniel
 */
public class Plane implements Geometry {
    Point3D _p;
    Vector _normal;


    /**
     * contractor - gets point and normal vector
     */
    public Plane(Point3D point, Vector normal)
    {
        _p = new Point3D(point);
        _normal = new Vector(normal);
    }

    /**
     * contractor - gets 3 points in the plane
     */
    public Plane(Point3D point1,Point3D point2,Point3D point3)
    {
        _p = new Point3D(point1);
        try {
            Vector v1 = new Vector(point2.subtract(point1));
            Vector v2 = new Vector(point3.subtract(point1));
            _normal = v1.crossProduct(v2).normalize();
        }
        catch (Exception e){}
    }


    /**
     * @return Point3D in the plane
     */
    public Point3D get_p() {
        return _p;
    }

    /**
     * @return Vector represents the normal to the plane
     */
    public Vector getNormal() {
        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "point=" + _p +
                ", vector normal=" + _normal +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return getNormal();
    }
}
