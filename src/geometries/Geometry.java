//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

/**
 * Geometry interface represents normal in geometry shapes
 * @author avi && daniel
 */
public abstract class Geometry implements Intersectable {
    protected Color _emmission;

    /**
     *
     */
    public Color get_emmission() {
        return _emmission;
    }

    /**
     *
     */
    public Geometry(Color _emmission) {
        this._emmission = _emmission;
    }

    /**
     * defult
     */
    public Geometry()
    {
        this(Color.BLACK);
    }

    /**
     * @param point Point3D
     * @return Vector - the normal vector
     */
    abstract public Vector getNormal(Point3D point);
}
