//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;


import java.util.LinkedList;
import java.util.List;

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
     * @param ray the direction of the Tube
     */
    public Tube(double radius, Ray ray) {
        super(radius);
        _axisRay = ray;
    }

    /**
     * contractor - gets double radius
     * @param radius of the Tube
     * @param ray the direction of the Tube
     * @param color of the Tube
     */
    public Tube(Color color, double radius, Ray ray) {
        this(radius, ray);
        _emmission = color;
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

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws Exception {
            Point3D ray_point = ray.get_point();

            Vector ray_vector = ray.get_vector(),
                    axisRay_vector = _axisRay.get_vector(),
                    DeltaPoints = new Vector(ray_point.subtract(_axisRay.get_point())),
                    temp1, temp2;

            if(ray_vector.isParallel(axisRay_vector)) // 0 Intersections
                return null;
           if(DeltaPoints.dotProduct(axisRay_vector) == 0) // 0 Intersections
            return null;
            double Vray_dot_Vaxis = ray_vector.dotProduct(axisRay_vector),
                    DeltaPoints_dot_Vaxis = DeltaPoints.dotProduct(axisRay_vector);

            temp1 = Vray_dot_Vaxis != 0 ? ray_vector.subtract(axisRay_vector.scale(Vray_dot_Vaxis)) : ray_vector;
            temp2 = DeltaPoints.subtract(axisRay_vector.scale(DeltaPoints_dot_Vaxis));

            double A = temp1.dotProduct(temp1);
            double B = Vray_dot_Vaxis != 0 ? 2 * ray_vector.subtract(axisRay_vector.scale(Vray_dot_Vaxis)).dotProduct(DeltaPoints.subtract(axisRay_vector.scale(DeltaPoints_dot_Vaxis))) : 2*ray_vector.dotProduct(DeltaPoints.subtract(axisRay_vector.scale(DeltaPoints_dot_Vaxis)));
            double C = temp2.dotProduct(temp2) - _radius * _radius;
            double delta = B * B - 4 * A * C;

            if (delta < 0) {// 0 Intersections
                return null;
            }
            LinkedList<GeoPoint> intersections = new LinkedList<GeoPoint>();
            double t1 = (-B + Math.sqrt(delta)) / (2 * A),
                    t2 = (-B - Math.sqrt(delta)) / (2 * A);

            if (delta == 0) {
                if (-B / (2 * A) < 0)
                    return null;
                intersections.add(new GeoPoint(this,new Vector(ray_point.add(ray_vector.scale(-B / (2 * A)))).getPoint()));// 1 Intersections
                return intersections;
            }
            else if (t1 < 0 && t2 < 0){
                return null;
            }
            else if (t1 < 0 && t2 > 0) {
                intersections.add(new GeoPoint(this,new Vector(ray_point.add(ray_vector.scale(t2))).getPoint()));
                return intersections;
            }
            else if (t1 > 0 && t2 < 0) {
                intersections.add(new GeoPoint(this,new Vector(ray_point.add(ray_vector.scale(t1))).getPoint()));
                return intersections;
            }
            else {
                intersections.add(new GeoPoint(this,new Vector(ray_point.add(ray_vector.scale(t1))).getPoint()));
                intersections.add(new GeoPoint(this,new Vector(ray_point.add(ray_vector.scale(t2))).getPoint()));
                return intersections;
            }

    }
}
