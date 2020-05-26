package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static primitives.Util.isZero;

public class Geometries implements Intersectable  {
    private List<Intersectable> GeometriesList;
    public Geometries()
    {
        GeometriesList = new LinkedList<>();
    }
    public Geometries(Intersectable... geometries){
        GeometriesList = new LinkedList<>();
        for (Intersectable shape:geometries) {
            GeometriesList.add(shape);
        }
    }
    public void add(Intersectable... geometries){
        if(GeometriesList == null)
            GeometriesList = new LinkedList<>();
        for (Intersectable shape:geometries) {
            GeometriesList.add(shape);
        }
    }
    private boolean isAlreadyInList(LinkedList<GeoPoint> list, Object object)
    {
        GeoPoint geoPoint = (GeoPoint) object;
        for (GeoPoint geoPoint1 : list) {
            if(isZero(geoPoint.point.get_x().get() - geoPoint1.point.get_x().get()) && isZero(geoPoint.point.get_y().get() - geoPoint1.point.get_y().get())
            && isZero(geoPoint.point.get_z().get() - geoPoint1.point.get_z().get()))
                return true;
        }
        return false;
    }
    /**
     * findIntersections - calculate the intersection points of a ray with geometry in a max distance
     * @param ray the ray we want to find the intersection points with geometry
     * @return the intersection points
     */
    @Override
    public List<GeoPoint> findIntersections(Ray ray, double max) throws Exception {
        List returnIntersectionsList = new LinkedList<GeoPoint>();
        List curShapeIntersections = new LinkedList<GeoPoint>();
        for (Intersectable shape:GeometriesList) {
            curShapeIntersections = shape.findIntersections(ray, max);
            if(curShapeIntersections != null)
            {
                for (Object geoPoint : curShapeIntersections) {
                    if(!isAlreadyInList((LinkedList<GeoPoint>)returnIntersectionsList, geoPoint))
                        returnIntersectionsList.add(geoPoint);
                }
            }
        }
        return returnIntersectionsList.size() == 0 ? null : returnIntersectionsList;
    }

}
