//Daniel Yochanan 322406232 && Avi Feder 208199638
package primitives;

import java.util.Objects;

/**
 * Vector class
 * represents Vector with a final point
 * @author avi && daniel
 */
public class Vector {
    Point3D _point;
    public Point3D getPoint() {
        return _point;
    }
    /**
     * contractor
     * @param x, y, z the coordinates
     */
    public Vector(double x, double y, double z) throws Exception
    {
        Point3D point = new Point3D(x, y, z);
        if( Point3D.ZERO.equals(point))
            throw new IllegalArgumentException("There is no support for the Vector Zero");
        _point = new Point3D(x, y, z);
    }
    /**
     * contractor
     * @param x, y, z the coordinates
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) throws Exception
    {
        Point3D point = new Point3D(x, y, z);
        if(Point3D.ZERO.equals(point))
            throw new IllegalArgumentException("There is no support for the Vector Zero");
        _point = new Point3D(x, y, z);
    }
    /**
     * contractor
     * @param point, the coordinates
     */
    public Vector(Point3D point) throws Exception
    {
        if( Point3D.ZERO.equals(point))
            throw new IllegalArgumentException("There is no support for the Vector Zero");
        _point = new Point3D(point.get_x(),point.get_y(),point.get_z());
    }
    /**
     * contractor
     * @param vec - the coordinates
     */
    public Vector(Vector vec)
    {
        Point3D point = vec.getPoint();
        _point = new Point3D(point.get_x(),point.get_y(),point.get_z());
    }
    /**
     * sub func
     * @param vector, the coordinates
     */
    public Vector subtract(Vector vector) throws Exception
    {
        try {
            return new Vector(_point.get_x().get() - vector._point.get_x().get(),
                    _point.get_y().get() - vector._point.get_y().get(), _point.get_z().get() - vector._point.get_z().get());
        }
        catch (Exception e) {
            throw e;
        }
    }
    /**
     * add func
     * @param vector, the coordinates
     */
    public Vector add(Vector vector) throws Exception
    {
        try {
            return new Vector(_point.get_x().get() + vector._point.get_x().get(),
                    _point.get_y().get() + vector._point.get_y().get(), _point.get_z().get() + vector._point.get_z().get());
        }
        catch (Exception e) {
            throw e;
        }
    }
    /**
     * scale func
     * @param scalar, the number to mult the coordinates
     */
    public Vector scale(double scalar) throws Exception
    {
        try {
            return new Vector(scalar * _point.get_x().get(), scalar * _point.get_y().get(), scalar * _point.get_z().get());
        }
        catch (Exception e) {
            throw e;
        }
    }
    /**
     * dotProduct func
     * @param vector, the vector we doing on the dot product
     */
    public double dotProduct (Vector vector)
    {
        return _point.get_x().get() * vector._point.get_x().get() +
                    _point.get_y().get() * vector._point.get_y().get()+ _point.get_z().get() * vector._point.get_z().get();
    }
    /**
     * crossProduct func
     * @param vector2, the second vector we doing on the cross product
     */
    public Vector crossProduct(Vector vector2) throws Exception
    {
        Coordinate x = new Coordinate(_point.get_y().get() * vector2._point.get_z().get() - _point.get_z().get() * vector2._point.get_y().get());
        Coordinate y = new Coordinate(_point.get_z().get() * vector2._point.get_x().get() - _point.get_x().get() * vector2._point.get_z().get());
        Coordinate z = new Coordinate(_point.get_x().get() * vector2._point.get_y().get() - _point.get_y().get() * vector2._point.get_x().get());
        try{
        return new Vector(x, y, z);
    }
        catch (Exception e) {
        throw e;
    }
    }
    /**
     * lengthSquared func
     * returns the length Squared of the vector
     */
    public double lengthSquared()
    {
        return _point.distanceSquared(Point3D.ZERO);
    }
    /**
     * length func
     * returns the length of the vector
     */
    public double length()
    {
        return Math.sqrt(lengthSquared());
    }
    /**
     * normalize func
     * normalized the vector
     */
    public Vector normalize()
    {
        double len = length();
        _point = new Point3D(_point.get_x().get()/len, _point.get_y().get()/len, _point.get_z().get()/len);
        return this;
    }
    /**
     * normalized func
     * return a temp normalized of the origin vector
     */
    public Vector normalized() throws Exception
    {
        Vector temp;
        try{
        temp = new Vector(_point);
    }
        catch (Exception e) {
        throw e;
    }
        return temp.normalize();
    }
    @Override
    public String toString() {
        return "" + _point;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vector)) return false;
        Vector vector = (Vector) o;
        return Objects.equals(getPoint(), vector.getPoint());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPoint());
    }
    public boolean isParallel(Vector vector2)
    {
        Coordinate x = new Coordinate(_point.get_y().get() * vector2._point.get_z().get() - _point.get_z().get() * vector2._point.get_y().get());
        Coordinate y = new Coordinate(_point.get_z().get() * vector2._point.get_x().get() - _point.get_x().get() * vector2._point.get_z().get());
        Coordinate z = new Coordinate(_point.get_x().get() * vector2._point.get_y().get() - _point.get_y().get() * vector2._point.get_x().get());
        Point3D point = new Point3D(x, y, z);
        if (point.equals(Point3D.ZERO))
            return true;
        return false;
    }
}
