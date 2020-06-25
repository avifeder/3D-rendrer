//Daniel Yochanan 322406232 && Avi Feder 208199638
package primitives;
import java.lang.Math;
import java.util.Objects;

/**
 * Point3D class
 * represents Point3D with 3 coordinates
 * @author avi && daniel
 */
public class Point3D {
    Coordinate _x;
    Coordinate _y;
    Coordinate _z;
    public static final Point3D ZERO  = new Point3D(0,0,0);

    public Coordinate get_x() {
        return _x;
    }

    public Coordinate get_y() {
        return _y;
    }

    public Coordinate get_z() {
        return _z;
    }
    /**
     * contractor
     * @param x, y, z the coordinates
     */
    public Point3D(double x, double y, double z)
    {
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }
    /**
     * copy contractor
     * @param point for the coordinates
     */
    public Point3D (Point3D point){
        _x = new Coordinate(point.get_x());
        _y = new Coordinate(point.get_y());
        _z = new Coordinate(point.get_z());
    }
    /**
     * contractor
     * @param x, y, z the coordinates
     */
    public Point3D (Coordinate x, Coordinate y, Coordinate z){
        _x = new Coordinate(x);
        _y = new Coordinate(y);
        _z = new Coordinate(z);
    }
    /**
     * sub func
     * @param point, the coordinates
     */
    public Vector subtract(Point3D point) throws Exception{
        try {
            return new Vector(_x.get() - point.get_x().get(), _y.get() - point.get_y().get(), _z.get() - point.get_z().get());
        }
        catch  (Exception e) {
            throw e;
        }
    }
    /**
     * add func
     * @param vector, the coordinates
     */
    public Point3D add(Vector vector)
    {
        return  new Point3D(_x.get() + vector._point.get_x().get(), _y.get() + vector._point.get_y().get(), _z.get() + vector._point.get_z().get());
    }
    /**
     * distanceSquared func
     * @param point, the coordinates
     */
    public double distanceSquared(Point3D point)
    {
        return (_x.get()-point._x.get()) * (_x.get()-point._x.get()) +
                (_y.get()-point._y.get()) * (_y.get()-point._y.get()) +
                (_z.get()-point._z.get()) * (_z.get()-point._z.get());
    }
    /**
     * distance func
     * @param point, the coordinates
     */
    public double distance(Point3D point)
    {
        return Math.sqrt(distanceSquared(point));
    }
    @Override
    public String toString() {
        return "(" +
                 _x +
                ", " + _y +
                ", " + _z +
                ')';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point3D)) return false;
        Point3D point3D = (Point3D) o;
        return Objects.equals(get_x(), point3D.get_x()) &&
                Objects.equals(get_y(), point3D.get_y()) &&
                Objects.equals(get_z(), point3D.get_z());
    }

    /**
     * Checks whether the different between the points is [almost] zero
     * @param point
     * @return true if the different between the points is zero or almost zero, false otherwise
     */
    public  boolean isAlmostEquals(Point3D point) {

        return  (Util.isZero(this._x.get()-point._x.get())) &&
                (Util.isZero(this._y.get()-point._y.get())) &&
                (Util.isZero(this._z.get()-point._z.get()));
    }
}
