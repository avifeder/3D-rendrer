package elements;

import primitives.Color;
/**
 * AmbientLight class
 * represents AmbientLight of a scene
 * @author avi && daniel
 */
public class AmbientLight {

    Color _intensity;
    /**
     * contractor
     * @param kA the Attenuation coefficient of the lamp light
     * @param iA, The intensity of the lamp light
     */
    public AmbientLight( double kA, Color iA) {
        this._intensity = iA.scale(kA);
    }

    /**
     * getIntensity
     * @return The intensity of the lamp light scale with the Attenuation coefficient of the lamp light
     */
    public Color getIntensity() {
        return _intensity;
    }
}
