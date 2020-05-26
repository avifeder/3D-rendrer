package elements;

import primitives.*;


/**
 * PointLight class
 * represents point light
 * @author avi && daniel
 */
public class PointLight  extends Light implements LightSource {
    protected Point3D _position;
    protected double _kC, _kL, _kQ;

    /**
     *
     * @param _intensity intensity of the point light
     * @param _position the position of the point light
     * @param _kC Attenuation coefficient
     * @param _kL Attenuation coefficient
     * @param kQ Attenuation coefficient
     */
    public PointLight(Color _intensity, Point3D _position, double _kC, double _kL, double kQ) {
        super(_intensity);
        this._position = _position;
        this._kC = _kC;
        this._kL = _kL;
        this._kQ = kQ;
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distanceSqrd = _position.distanceSquared(p);
        double distance = Math.sqrt(distanceSqrd);
        return get_intensity().reduce(_kC + _kL*distance + _kQ*distanceSqrd);
    }

    @Override
    public Vector getL(Point3D p) {
        try {
            return p.subtract(_position).normalize();
        }catch (Exception e){
            return null;
        }

    }
    /**
     * getDistance - calculate the distance from light to the point
     * @param point the point we want to find the distance from
     * @return the distance from light to the point
     */
    @Override
    public double getDistance(Point3D point) {
        return this._position.distance(point);
    }
}
