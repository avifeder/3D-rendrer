package elements;

import primitives.Color;

/**
 * AmbientLight class
 * represents AmbientLight of a scene
 * @author avi && daniel
 */
public class AmbientLight extends Light{
    /**
     * contractor
     * @param kA the Attenuation coefficient of the lamp light
     * @param iA, The intensity of the lamp light
     */
    public AmbientLight(double kA, Color iA) {
        super(iA.scale(kA));
    }
}
