package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 *
 */
public interface Intersectable {
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint)) return false;
            GeoPoint geoPoint = (GeoPoint) o;
            return Objects.equals(geometry, geoPoint.geometry) &&
                    Objects.equals(point, geoPoint.point);
        }
    }
    /**
     * findIntersections - calculate the intersection points of a ray with geometry
     * @param ray the ray we want to find the intersection points with geometry
     * @return the intersection points
     */
    default List<GeoPoint> findIntersections(Ray ray) throws Exception{
        return findIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**
     * findIntersections - calculate the intersection points of a ray with geometry in a max distance
     * @param ray the ray we want to find the intersection points with geometry
     * @return the intersection points
     */
    List<GeoPoint> findIntersections(Ray ray, double max)throws Exception;

}
