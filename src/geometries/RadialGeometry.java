//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;


/**
 * RadialGeometry abstract class implements Geometry
 * represents geometry shapes with radius by vector and point
 * @author avi && daniel
 */
public abstract class RadialGeometry extends Geometry{
    double _radius;

    /**
     * @return radius of the shape
     */
    public double get_radius() {
        return _radius;
    }

    /**
     * contractor - gets double radius
     * @param radius of the shape
     */
    public RadialGeometry(double radius)
    {
        _radius = radius;
    }

    /**
     * copy contractor - RadialGeometry radialGeometry
     * @param radialGeometry of another shape
     */
    public RadialGeometry(RadialGeometry radialGeometry)
    {
        _radius = radialGeometry.get_radius();
    }
}
