//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class Triangle extends Polygon {
    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param point1 vertices according to their order by edge path
     * @param point2 vertices according to their order by edge path
     * @param point3 vertices according to their order by edge path
     * @throws IllegalArgumentException in any case of illegal combination of
     *                                  vertices:
     *                                  <ul>
     *                                  <li>Less than 3 vertices</li>
     *                                  <li>Consequent vertices are in the same
     *                                  point
     *                                  <li>The vertices are not in the same
     *                                  plane</li>
     *                                  <li>The order of vertices is not according
     *                                  to edge path</li>
     *                                  <li>Three consequent vertices lay in the
     *                                  same line (180&#176; angle between two
     *                                  consequent edges)
     *                                  <li>The polygon is concave (not convex></li>
     *                                  </ul>
     */
    public Triangle(Point3D point1, Point3D point2, Point3D point3) throws Exception {
        super(point1, point2, point3);
    }
    @Override
    public List<Point3D> findIntersections(Ray ray) throws Exception{
        if(ray.get_point().equals(this._vertices.get(0)) || ray.get_point().equals(this._vertices.get(1)) || ray.get_point().equals(this._vertices.get(2)))
            return null;
        Vector v1 = this._vertices.get(0).subtract(ray.get_point());
        Vector v2 = this._vertices.get(1).subtract(ray.get_point());
        Vector v3 = this._vertices.get(2).subtract(ray.get_point());
        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();
        double vn1 = ray.get_vector().dotProduct(N1);
        double vn2 = ray.get_vector().dotProduct(N2);
        double vn3 = ray.get_vector().dotProduct(N3);
        if(isZero(vn1) || isZero(vn2) || isZero(vn3))
            return null;
        if(vn1 > 0 && vn2 > 0 && vn3 > 0)
            return this._plane.findIntersections(ray);
        if(vn1 < 0 && vn2 < 0 && vn3 < 0)
            return this._plane.findIntersections(ray);
        return null;
    }
}
