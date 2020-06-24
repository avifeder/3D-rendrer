/**
 *
 */
package unittests;

import geometries.Cylinder;
import geometries.Plane;
import org.junit.Test;

import elements.*;
import geometries.Sphere;
import geometries.Triangle;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 *
 */
public class ReflectionRefractionTests {

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() throws  Exception{
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0, Color.BLACK));

        scene.addGeometries(
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.4, 0.3, 100, 0.3, 0), 50,
                        new Point3D(0, 0, 50)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.5, 0.5, 100), 25, new Point3D(0, 0, 50)));

        scene.addLights(new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("twoSpheres", 150, 150, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors()throws Exception {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -10000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(10000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0.1, new Color(255, 255, 255) ));

        scene.addGeometries(
                new Sphere(new Color(0, 0, 100), new Material(0.25, 0.25, 20, 0.5, 0), 400, new Point3D(-950, 900, 1000)),
                new Sphere(new Color(100, 20, 20), new Material(0.25, 0.25, 20), 200, new Point3D(-950, 900, 1000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 1), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(670, -670, -3000)),
                new Triangle(new Color(20, 20, 20), new Material(0, 0, 0, 0, 0.5), new Point3D(1500, 1500, 1500),
                        new Point3D(-1500, -1500, 1500), new Point3D(-1500, 1500, 2000)));

        scene.addLights(new SpotLight(new Color(1020, 400, 400),  new Point3D(-750, 750, 150),
                new Vector(-1, 1, 4), 1, 0.00001, 0.000005));

        ImageWriter imageWriter = new ImageWriter("twoSpheresMirrored", 2500, 2500, 500, 500);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially transparent Sphere
     *  producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere()throws Exception {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries( //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(Color.BLACK, new Material(0.5, 0.5, 60), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.BLUE), new Material(0.2, 0.2, 30, 0.6, 0), // )
                        30, new Point3D(60, -50, 50)));

        scene.addLights(new SpotLight(new Color(700, 400, 400), //
                new Point3D(60, -50, 0), new Vector(0, 0, 1), 1, 4E-5, 2E-7));

        ImageWriter imageWriter = new ImageWriter("shadow with transparency", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles and two Spheres
     */
    @Test
    public void multiShapesTransparent()throws Exception {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(1000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0.15, new Color(java.awt.Color.WHITE)));

        scene.addGeometries( //
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 60, 0, 1), //
                        new Point3D(-150, 150, 115), new Point3D(150, 150, 135), new Point3D(75, -75, 150)), //
                new Triangle(new Color(java.awt.Color.BLACK), new Material(0.5, 0.5, 60, 0, 1), //
                        new Point3D(-150, 150, 115), new Point3D(-70, -70, 140), new Point3D(75, -75, 150)), //
                new Sphere(new Color(java.awt.Color.CYAN), new Material(0.2, 0.2, 30, 0, 0), // )
                        30, new Point3D(40, -60, -150)),
                new Sphere(new Color(java.awt.Color.RED), new Material(0.4, 0.3, 100, 0.4, 0), 50,
                            new Point3D(-55, 55, 50)));

        scene.addLights(
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, 100, -500), new Vector(-1, 1, 2), 1,
                        0.0004, 0.0000006));

        ImageWriter imageWriter = new ImageWriter("multiShapesTransparent", 200, 200, 600, 600);
        Render render = new Render(imageWriter, scene);

        render.setMultiThreading(4);
        render.renderImage();
        render.writeToImage();
    }

    /**
     * bonus with 10 shapes
     */
    @Test
    public void bonusMultiShapes()throws Exception {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0.1, new Color(0, 0, 0)));

        scene.addGeometries(
                new Sphere(new Color(0, 25, 51), new Material(0.8, 0.8, 200,0,0.7), 400, new Point3D(-950, 0, 1100)),
                new Sphere(new Color(32, 32, 32), new Material(0.8, 0.8, 200, 0,0.7), 300, new Point3D(0, 300, 900)),
                new Sphere(new Color(114, 107, 100), new Material(1, 0.8, 900, 0,1), 200, new Point3D(1000, 650, 640)),
                new Sphere(new Color(63, 60, 77), new Material(0.8, 0.25, 120, 0, 0.7), 500, new Point3D(1000, -150, 1100)),
                new Sphere(new Color (51, 0, 51) ,new Material(0.85, 0.25, 700, 0, 0.7), 600, new Point3D(0, -700, 1600)),
                new Plane(new Color(java.awt.Color.black), new Material(0.4, 0.3, 20000, 0, 0.4), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));



        scene.addLights
                (
                new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0))
                );
        ImageWriter imageWriter = new ImageWriter("our test", 2500, 2500, 800, 800);
        Render render = new Render(imageWriter, scene);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void multiMirror()throws Exception {
        Scene scene = new Scene("Test scene");
        scene.set_camera(new Camera(new Point3D(50, 100, -11000), new Vector(0, 0, 1), new Vector(0, -1, 0)));
        scene.set_distance(9000);
        scene.set_background(Color.BLACK);
        scene.set_ambientLight(new AmbientLight(0.1, new Color(0, 0, 0)));

        scene.addGeometries(
                new Sphere(new Color(0, 25, 51),  new Material(0.8, 0.8, 200,0,0.7), 200, new Point3D(-1000, -50, 1600)),
                new Sphere(new Color(0, 25, 51), new Material(0.8, 0.8, 200,0,0.7), 200, new Point3D(1000, -50, 1600)),
                new Sphere(new Color (0, 25, 51), new Material(0.8, 0.8, 200,0,0.7), 200, new Point3D(0, -50, 1600)),
                new Triangle(new Color (java.awt.Color.WHITE).reduce(10), new Material(0, 0, 0,1,0,0.00000001), new Point3D(40, -15, -8200),new Point3D(350, -15, -8200),new Point3D(350, 185, -8200)),
                new Triangle(new Color (java.awt.Color.WHITE).reduce(10), new Material(0, 0, 0,1,0,20), new Point3D(40, -15, -8200),new Point3D(350, 185, -8200),new Point3D(-270, 185, -8200)),
                new Triangle(new Color (java.awt.Color.WHITE).reduce(10), new Material(0, 0, 0,1,0, 35), new Point3D(40, -15, -8200),new Point3D(-270, -15, -8200),new Point3D(-270, 185, -8200)),

                new Plane(new Color(java.awt.Color.black).reduce(5), new Material(0.4, 0.3, 20000, 0, 0), new Point3D(1500, 1500, 0),
                        new Point3D(-1500, -1500, 3850), new Point3D(-1500, 1500, 0)));



        scene.addLights
                (
                        new SpotLight(new Color(1020, 400, 400),  new Point3D(0, 300, -400), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                        new SpotLight(new Color(20, 40, 0),  new Point3D(800, 100, -300), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                        new SpotLight(new Color(1020, 400, 400),  new Point3D(-800, 100, -300), new Vector(-1, 1, 4), 1, 0.00001, 0.000005),
                        new DirectionalLight(new Color(java.awt.Color.darkGray),   new Vector(-0.5, 0.5, 0))
                );

        ImageWriter imageWriter = new ImageWriter("multiMirrorTest", 2500, 2500, 1000, 1000);
        Render render = new Render(imageWriter, scene);
        render.setDistanceForDiffusedAndGlossy(5000);
        render.renderImage();
        render.writeToImage();
    }

}
