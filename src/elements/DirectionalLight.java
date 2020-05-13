package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;


/**
 * DirectionalLight class
 * represents sun light
 * @author avi && daniel
 */
public class DirectionalLight  extends Light implements LightSource{

    private Vector _direction;

    /**
     * contractor
     * @param _intensity the intensity of the light
     * @param _direction the direction of the light
     */
    public DirectionalLight(Color _intensity, Vector _direction) {
        super(_intensity);
        this._direction = _direction.normalize();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return get_intensity();
    }

    @Override
    public Vector getL(Point3D p) {
        return _direction;
    }
}
