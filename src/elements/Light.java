package elements;

import primitives.Color;

abstract class Light {
    protected Color _intensity;

    public Light(Color _intensity) {
        this._intensity = _intensity;
    }

    public Color get_intensity() {
        return _intensity;
    }
}
