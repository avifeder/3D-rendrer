package elements;

import primitives.Color;

/**
 * Light abstract class
 * represents basic light
 * @author avi && daniel
 */
abstract class Light {

    protected Color _intensity;

    /**
     * constructor
     * @param _intensity represents basic intensity
     */
    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    /**
     * getter for _intensity
     * @return the intensity
     */
    public Color get_intensity() {
        return _intensity;
    }
}
