//Daniel Yochanan 322406232 && Avi Feder 208199638
package geometries;

import primitives.*;

/**
 * Geometry interface represents normal in geometry shapes
 * @author avi && daniel
 */
public abstract class Geometry implements Intersectable {
    protected Color _emmission;

    protected Material _material;

    /**
     * constructor of Geometry class
     * @param _emmission gets the emmission light
     * @param _material gets the material
     */
    public Geometry(Color _emmission, Material _material) {
        this._emmission = _emmission;
        this._material = _material;
    }

    /**
     * constructor of Geometry class
     * @param _emmission gets the emmission light
     */
    public Geometry(Color _emmission) {
        this(_emmission, new Material(0, 0, 0));
    }

    /**
     * default constructor of Geometry class
     */
    public Geometry()
    {
        this(Color.BLACK);
    }

    /**
     * @return the emmission light
     */
    public Color get_emmission() {
        return _emmission;
    }

    /**
     * @return the material of the geometry
     */
    public Material get_material() {
        return _material;
    }

    /**
     * @param point Point3D
     * @return Vector - the normal vector
     */
    abstract public Vector getNormal(Point3D point);
}
