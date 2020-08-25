package elements;

import primitives.*;

/**
 * LightSource interface
 * represents basic light source
 * @author avi && daniel
 */
public interface LightSource {

    /**
     * getter for _intensity
     * @param p the point of the intensity
     * @return the intensity
     */
    public Color getIntensity(Point3D p);

    /**
     *
     * @param p the point of the intensity
     * @return the direction vector of the light
     */
    public Vector getL(Point3D p);
    /**
     * getDistance - calculate the distance from light to the point
     * @param point the point we want to find the distance from
     * @return the distance from light to the point
     */
    double getDistance(Point3D point);

}
