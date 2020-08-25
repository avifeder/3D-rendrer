//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;


import primitives.*;


/**
 * Cylinder class represents cylinder
 * @author avi && daniel
 */
public class Cylinder extends Tube {

    double _height;

    public double get_height() {
        return _height;
    }

    /**
     * @param radius double
     * @param ray    Ray
     * @param height double
     */
    public Cylinder(double radius, Ray ray, double height) {
        super(radius, ray);
        _height = height;
    }

    /**
     * @param radius double
     * @param ray    Ray
     * @param height double
     * @param color  Color
     */
    public Cylinder(Color color,double radius, Ray ray, double height) {
        super(color, radius, ray);
        _height = height;
    }

    /**
     * @param radius double
     * @param ray    Ray
     * @param height double
     * @param color  Color
     * @param material  material
     */
    public Cylinder(Color color, Material material, double radius, Ray ray, double height) {
        super(color,material, radius, ray);
        _height = height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
        "Ray =" + _axisRay + " , Radius =" + _radius +
                " , height=" + _height +
                '}';
    }


    /**
     * getNormal - gets the normal of the cylinder
     * @param point of the Tube
     */
    @Override
    public Vector getNormal(Point3D point)
    {
        try {
            Vector v1 = new Vector(point.subtract(_axisRay.get_point()));
            if(_axisRay.get_vector().dotProduct(v1) == 0)
            {
                    if (_axisRay.get_point().distance(point) < _radius)
                             return _axisRay.get_vector();
                    return point.subtract(_axisRay.get_point());
            }
            Vector v2 = new Vector(_axisRay.get_vector().scale(_axisRay.get_vector().dotProduct(v1)));
            if(v1.equals(v2))
                return _axisRay.get_vector();
            else {
                Vector v3 = new Vector(v1.subtract(v2));
                return v3.length() == _radius ? v3.normalize() : _axisRay.get_vector();
            }
        }
        catch (Exception e){
            return null;}
    }
}
