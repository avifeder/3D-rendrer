//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

import java.util.List;
import static primitives.Util.*;

/**
 * Plane class implements Geometry
 * represents plane by vector and point
 * @author avi && daniel
 */
public class Plane extends Geometry {
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
     * contractor - gets point, color and normal vector
     */
    public Plane(Color color,Point3D point, Vector normal)
    {
        super(color);
        _p = new Point3D(point);
        _normal = new Vector(normal);
    }

    /**
     * contractor - gets point, color, material and normal vector
     */
    public Plane(Color color, Material material,Point3D point, Vector normal)
    {
        super(color,material);
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
     * contractor - gets 3 points in the plane and color
     */
    public Plane(Color color,Point3D point1,Point3D point2,Point3D point3)
    {
        this(point1, point2, point3);
        _emmission = color;
    }

    /**
     * contractor - gets 3 points in the plane, color and material
     */
    public Plane(Color color, Material material,Point3D point1,Point3D point2,Point3D point3)
    {
        this(color, point1, point2, point3);
        _material = material;
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
    /**
     * findIntersections - calculate the intersection points of a ray with geometry in a max distance
     * @param ray the ray we want to find the intersection points with geometry
     * @return the intersection points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) throws Exception{
        if(ray.get_point().equals(this._p))
            return null;
        double mone = this._normal.dotProduct(this._p.subtract(ray.get_point()));
        double mechane = this._normal.dotProduct(ray.get_vector());
        if(isZero(mone) || isZero(mechane))
            return null;
        double t = mone / mechane;
        if(t <= 0 || alignZero((t - max)) > 0)
            return null;
        return List.of(new GeoPoint(this, ray.getPoint(t)));
    }

}
