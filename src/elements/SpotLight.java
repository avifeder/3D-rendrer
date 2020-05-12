package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

public class SpotLight extends PointLight {
    private Vector _direction;

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
