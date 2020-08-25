package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * SpotLight class
 * represents spot light
 * @author avi && daniel
 */
public class SpotLight extends PointLight {
    private Vector _direction;

    /**
     *
     * @param _intensity intensity of the spot light
     * @param _position the position of the spot light
     * @param _direction the direction of the spot light
     * @param _kC Attenuation coefficient
     * @param _kL Attenuation coefficient
     * @param kQ Attenuation coefficient
     */
    public SpotLight(Color _intensity, Point3D _position , Vector _direction, double _kC, double _kL, double kQ) {
        super(_intensity, _position, _kC, _kL, kQ);
        this._direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        double distanceSqrd = _position.distanceSquared(p);
        double distance = Math.sqrt(distanceSqrd);
        Vector L = getL(p);
        if(L == null)
            L = _direction;
        double cos = Math.max(0 , _direction.dotProduct(getL(p)));
        return get_intensity().scale(cos).reduce(_kC + _kL*distance + _kQ*distanceSqrd);
    }

}
