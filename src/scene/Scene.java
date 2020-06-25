package scene;

import elements.AmbientLight;
import elements.Camera;
import elements.LightSource;
import geometries.Geometries;
import geometries.Intersectable;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    String _name;
    Color _background;
    AmbientLight _ambientLight;
    Geometries _geometries;
    Camera _camera;
    double _distance;
    List<LightSource> _lights;


    public Scene(String _name) {
        this._name = _name;
        _geometries = new Geometries();
        _lights = new LinkedList<LightSource>();
    }

    public String get_name() {
        return _name;
    }

    /**
     * get the list of the lights
     * @return lights source list
     */
    public List<LightSource> get_lights() {
        return _lights;
    }

    /**
     * add lights to the list
     * @param lights list of lights
     */
    public void addLights(LightSource... lights) {
        for (int i=0; i<lights.length;i++)
        {
            _lights.add(lights[i]);
        }
    }

    public Color get_background() {
        return _background;
    }

    public void set_background(Color _background) {
        this._background = _background;
    }

    public AmbientLight get_ambientLight() {
        return _ambientLight;
    }

    public void set_ambientLight(AmbientLight _ambientLight) {
        this._ambientLight = _ambientLight;
    }

    public Camera get_camera() {
        return _camera;
    }

    public void set_camera(Camera _camera) { this._camera = _camera; }

    public double get_distance() {
        return _distance;
    }

    public void set_distance(double _distance) {
        this._distance = _distance;
    }

    public Geometries get_geometries() {
        return _geometries;
    }

    public void addGeometries(Intersectable... geometries) {
        for (int i = 0; i < geometries.length; ++i) {
            _geometries.add(geometries[i]);
        }
    }

}
