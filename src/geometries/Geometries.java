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
    private boolean isAlreadyInList(ArrayList<Point3D> list, Object object)
    {
        Point3D point = (Point3D)object;
        for (Point3D point1 : list) {
            if(isZero(point.get_x().get() - point1.get_x().get()) && isZero(point.get_y().get() - point1.get_y().get())
            && isZero(point.get_z().get() - point1.get_z().get()))
                return true;
        }
        return false;
    }
    @Override
    public List<Point3D> findIntersections(Ray ray) throws Exception {
        List returnIntersectionsList = new ArrayList();
        List curShapeIntersections = new ArrayList<Point3D>();
        for (Intersectable shape:GeometriesList) {
            curShapeIntersections = shape.findIntersections(ray);
            if(curShapeIntersections != null)
            {
                for (Object point : curShapeIntersections) {
                    if(!isAlreadyInList((ArrayList<Point3D>)returnIntersectionsList, point))
                        returnIntersectionsList.add(point);
                }
            }
        }
        return returnIntersectionsList.size() == 0 ? null : returnIntersectionsList;
    }
}
