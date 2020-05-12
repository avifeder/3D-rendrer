//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import java.util.ArrayList;
import java.util.List;
import primitives.*;
import static primitives.Util.*;

/**
 * Polygon class represents two-dimensional polygon in 3D Cartesian coordinate
 * system
 *
 * @author Dan
 */
public class Polygon extends Geometry {
    /**
     * List of polygon's vertices
     */
    protected List<Point3D> _vertices;
    /**
     * Associated plane in which the polygon lays
     */
    protected Plane _plane;

    public List<Point3D> get_vertices() {
        return _vertices;
    }

    public Plane get_plane() {
        return _plane;
    }

    /**
     * Polygon constructor based on vertices list. The list must be ordered by edge
     * path. The polygon must be convex.
     *
     * @param vertices list of vertices according to their order by edge path
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
    public Polygon(Point3D... vertices) throws Exception{
        if (vertices.length < 3)
            throw new IllegalArgumentException("A polygon can't have less than 3 vertices");
        _vertices = List.of(vertices);
        // Generate the plane according to the first three vertices and associate the
        // polygon with this plane.
        // The plane holds the invariant normal (orthogonal unit) vector to the polygon
        _plane = new Plane(vertices[0], vertices[1], vertices[2]);
        if (vertices.length == 3) return; // no need for more tests for a Triangle

        Vector n = _plane.getNormal();
try {
    // Subtracting any subsequent points will throw an IllegalArgumentException
    // because of Zero Vector if they are in the same point
    Vector edge1 = vertices[vertices.length - 1].subtract(vertices[vertices.length - 2]);
    Vector edge2 = vertices[0].subtract(vertices[vertices.length - 1]);

    // Cross Product of any subsequent edges will throw an IllegalArgumentException
    // because of Zero Vector if they connect three vertices that lay in the same
    // line.
    // Generate the direction of the polygon according to the angle between last and
    // first edge being less than 180 deg. It is hold by the sign of its dot product
    // with
    // the normal. If all the rest consequent edges will generate the same sign -
    // the
    // polygon is convex ("kamur" in Hebrew).
    boolean positive = edge1.crossProduct(edge2).dotProduct(n) > 0;
    for (int i = 1; i < vertices.length; ++i) {
        // Test that the point is in the same plane as calculated originally
        if (!isZero(vertices[i].subtract(vertices[0]).dotProduct(n)))
            throw new IllegalArgumentException("All vertices of a polygon must lay in the same plane");
        // Test the consequent edges have
        edge1 = edge2;
        edge2 = vertices[i].subtract(vertices[i - 1]);
        if (positive != (edge1.crossProduct(edge2).dotProduct(n) > 0))
            throw new IllegalArgumentException("All vertices must be ordered and the polygon must be convex");
    }
}
catch (Exception e)
{
    throw e;
}
    }

    /**
     * contractor - gets points and color
     */
    public Polygon(Color color, Point3D... vertices) throws Exception{
       this(vertices);
       _emmission = color;
    }

    @Override
    public String toString() {
        return "Polygon{" +
                "vertices=" + _vertices +
                ", plane=" + _plane +
                '}';
    }

    @Override
    public Vector getNormal(Point3D point) {
        return _plane.getNormal();
    }

    @Override
    public List<GeoPoint> findIntersections(Ray ray) throws Exception{
        for (int i = 0; i < _vertices.size(); ++i){
            if(ray.get_point().equals(this._vertices.get(i)))
                return null;
        }
        Vector [] v1ToVn = new Vector[this._vertices.size()];
        for (int i = 0; i < _vertices.size(); ++i) {
        v1ToVn[i] = this._vertices.get(i).subtract(ray.get_point());
        }
        for (int i = 0; i < v1ToVn.length; ++i){
            if(v1ToVn[i].isParallel(v1ToVn[(i + 1) % v1ToVn.length]))
                return null;
        }
        Vector [] N1ToNn = new Vector[this._vertices.size()];
        for (int i = 0; i < _vertices.size(); ++i) {
            N1ToNn[i] = v1ToVn[i].crossProduct(v1ToVn[(i + 1) % v1ToVn.length]).normalize();
        }
        double [] vn1ToVNn = new double[this._vertices.size()];
        for (int i = 0; i < _vertices.size(); ++i) {
            vn1ToVNn[i] = ray.get_vector().dotProduct(N1ToNn[i]);
        }
        for (int i = 0; i < _vertices.size(); ++i){
            if(isZero(vn1ToVNn[i]))
                return null;
        }
        boolean isAllPositive = true,
        isAllNegative = true;
        for (int i = 0; i < _vertices.size(); ++i){
            if(vn1ToVNn[i] < 0 && isAllPositive)
                isAllPositive = false;
        }
        for (int i = 0; i < _vertices.size(); ++i){
            if(vn1ToVNn[i] > 0 && isAllNegative)
                isAllNegative = false;
        }
        List<GeoPoint> Intersections = null;
        if(isAllNegative || isAllPositive){
            Intersections = this._plane.findIntersections(ray);
            for (int i = 0; i < Intersections.size(); i++)
                Intersections.get(i).geometry = this;
            return Intersections;
        }
        return null;
    }
}
