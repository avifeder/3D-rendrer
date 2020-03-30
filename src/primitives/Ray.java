//Daniel Yochanan 322406232 && Avi Feder 208199638
package primitives;

import java.util.Objects;

/**
 * Ray class
 * represents Ray with point and vector
 * @author avi && daniel
 */
public class Ray {
    Point3D _point;
    Vector _vector;

    public Point3D get_point() {
        return _point;
    }

    public Vector get_vector() {
        return _vector;
    }
    /**
     * contractor
     * @param point and vector
     */
    public Ray(Point3D point, Vector vector) throws Exception
    {
        _point = new Point3D(point);
        try {
            _vector = new Vector(vector.normalized());
        }
        catch (Exception e)
        {
            throw e;
        }

    }
    /**
     * contractor
     * @param ray, a Ray
     */
    public Ray(Ray ray)
    {
        _point = new Point3D(ray._point);
        _vector = new Vector(ray._vector);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "point=" + _point +
                ", vector=" + _vector +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray)) return false;
        Ray ray = (Ray) o;
        return Objects.equals(get_point(), ray.get_point()) &&
                Objects.equals(get_vector(), ray.get_vector());
    }

    @Override
    public int hashCode() {
        return Objects.hash(get_point(), get_vector());
    }
}
