//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import static primitives.Util.isZero;


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
     * @param p center of the Sphere
     */
    public Sphere(double radius, Point3D p) {
        super(radius);
        _center = p;
    }

    /**
     * contractor - gets double radius
     * @param radius of the Sphere
     * @param p center of the Sphere
     * @param color of the Sphere
     */
    public Sphere(Color color, double radius, Point3D p) {
        this(radius, p);
        _emmission = color;
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

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws Exception {
        if(this._center.equals(ray.get_point()))
            return List.of(new GeoPoint(this, _center.add(ray.get_vector().scale(_radius))));
        Vector u = this._center.subtract(ray.get_point());
        double tm = ray.get_vector().dotProduct(u);
        double d = Math.sqrt(u.lengthSquared()-tm*tm);
        if(d >= this._radius)
            return null;
        double th =  Math.sqrt(this._radius*this._radius-d*d);
        double t1 = tm + th;
        double t2 = tm - th;
        if(isZero(t1) && isZero(t2))
            return  null;
        if(t1 < 0 && t2 <0)
            return  null;
        if(isZero(t1) && t2 <0)
            return  null;
        if(t1 < 0 && isZero(t2))
            return  null;
        List list = new LinkedList<GeoPoint>();
        if(t1 > 0)
            list.add(new GeoPoint(this, ray.getPoint(t1)));
        if(t2 > 0)
            list.add(new GeoPoint(this,ray.getPoint(t2)));
        return list;
    }
}
