//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

import java.util.LinkedList;
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

    /**
     * @param color of the Triangle
     * @param point1 point of the Triangle
     * @param point2 point of the Triangle
     * @param point3 point of the Triangle
     */
    public Triangle(Color color, Point3D point1, Point3D point2, Point3D point3) throws Exception {
        super(color, point1, point2, point3);
    }
    /**
     * @param color of the Triangle
     * @param point1 point of the Triangle
     * @param point2 point of the Triangle
     * @param point3 point of the Triangle
     * @param material material of the Triangle
     */
    public Triangle(Color color,Material material, Point3D point1, Point3D point2, Point3D point3) throws Exception {
        super(color,material, point1, point2, point3);
    }
    /**
     * findIntersections - calculate the intersection points of a ray with geometry in a max distance
     * @param ray the ray we want to find the intersection points with geometry
     * @return the intersection points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) throws Exception{
        if(ray.get_point().equals(this._vertices.get(0)) || ray.get_point().equals(this._vertices.get(1)) || ray.get_point().equals(this._vertices.get(2)))
            return null;
        Vector v1 = this._vertices.get(0).subtract(ray.get_point());
        Vector v2 = this._vertices.get(1).subtract(ray.get_point());
        Vector v3 = this._vertices.get(2).subtract(ray.get_point());
        if(v1.isParallel(v2) || v2.isParallel(v3) || v3.isParallel(v1))
            return null;
        Vector N1 = v1.crossProduct(v2).normalize();
        Vector N2 = v2.crossProduct(v3).normalize();
        Vector N3 = v3.crossProduct(v1).normalize();
        double vn1 = ray.get_vector().dotProduct(N1);
        double vn2 = ray.get_vector().dotProduct(N2);
        double vn3 = ray.get_vector().dotProduct(N3);
        if(isZero(vn1) || isZero(vn2) || isZero(vn3))
            return null;
        List<GeoPoint> Intersections = null;

        if(vn1 > 0 && vn2 > 0 && vn3 > 0) {
            Intersections = this._plane.findIntersections(ray, max);
            if(Intersections != null) {
                for (int i = 0; i < Intersections.size(); i++)
                    Intersections.get(i).geometry = this;
            }
            return Intersections;
        }
        if(vn1 < 0 && vn2 < 0 && vn3 < 0) {
            Intersections = this._plane.findIntersections(ray, max);
            if(Intersections != null) {
                for (int i = 0; i < Intersections.size(); i++)
                    Intersections.get(i).geometry = this;
            }
            return Intersections;
        }
        return null;
    }
}
