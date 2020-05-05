package xml;
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import elements.AmbientLight;
import elements.Camera;
import geometries.Geometries;
import geometries.Sphere;
import geometries.Triangle;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import primitives.Color;
import primitives.Point3D;
import primitives.Vector;
import renderer.ImageWriter;
import renderer.Render;
import scene.Scene;

public class xmlToScene {

    public static void convertXmlToScene(String path) {

            try {
                File file = new File(path);
                DocumentBuilder dBuilder = DocumentBuilderFactory.newInstance()
                        .newDocumentBuilder();

                Document doc = dBuilder.parse(file);
                double width = Double.parseDouble(doc.getDocumentElement().getAttributeNode("screen-width").getValue());
                String[] sceneColor = doc.getDocumentElement().getAttributeNode("background-color").getValue().split(" ");
                double height = Double.parseDouble(doc.getDocumentElement().getAttributeNode("screen-height").getValue());
                double distance = Double.parseDouble(doc.getDocumentElement().getAttributeNode("screen-distance").getValue());
                Color color = new Color(Double.parseDouble(sceneColor[0]), Double.parseDouble(sceneColor[1]), Double.parseDouble(sceneColor[2]));
                int nY = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("image").item(0).getAttributes().item(1).getNodeValue());
                int nX = Integer.parseInt(doc.getDocumentElement().getElementsByTagName("image").item(0).getAttributes().item(0).getNodeValue());
                String[] point = doc.getDocumentElement().getElementsByTagName("camera").item(0).getAttributes().item(0).getNodeValue().split(" ");
                Point3D p0= new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
                String[] vector = doc.getDocumentElement().getElementsByTagName("camera").item(0).getAttributes().item(1).getNodeValue().split(" ");
                Vector vto = new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1]), Double.parseDouble(vector[2]));
                vector = doc.getDocumentElement().getElementsByTagName("camera").item(0).getAttributes().item(2).getNodeValue().split(" ");
                Vector vup = new Vector(Double.parseDouble(vector[0]), Double.parseDouble(vector[1]), Double.parseDouble(vector[2]));
                Camera camera = new Camera(p0, vto, vup);
                String[] ambientLight_Color = doc.getDocumentElement().getElementsByTagName("ambient-light").item(0).getAttributes().item(0).getNodeValue().split(" ");
                Color ambientLightColor = new Color(Double.parseDouble(ambientLight_Color[0]), Double.parseDouble(ambientLight_Color[1]), Double.parseDouble(ambientLight_Color[2]));
                Geometries geometries = new Geometries();
                for(int i=1; i<doc.getDocumentElement().getElementsByTagName("geometries").item(0).getChildNodes().getLength();i+=2) {
                    Node node = doc.getDocumentElement().getElementsByTagName("geometries").item(0).getChildNodes().item(i);
                    String name = node.getNodeName();
                    if (name == "triangle") {
                        point = node.getAttributes().item(0).getNodeValue().split(" ");
                        p0 = new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
                        point = node.getAttributes().item(1).getNodeValue().split(" ");
                        Point3D p1 = new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
                        point = node.getAttributes().item(2).getNodeValue().split(" ");
                        Point3D p2 = new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
                        Triangle triangle = new Triangle(p0, p1, p2);
                        geometries.add(triangle);
                    } else if (name == "sphere") {
                        point = node.getAttributes().item(0).getNodeValue().split(" ");
                        Point3D center= new Point3D(Double.parseDouble(point[0]), Double.parseDouble(point[1]), Double.parseDouble(point[2]));
                        double radius = Double.parseDouble(node.getAttributes().item(1).getNodeValue());
                        Sphere sphere = new Sphere(radius, center);
                        geometries.add(sphere);
                    }
                }
                Scene scene = new Scene("my_xml_scene");
                scene.set_ambientLight(new AmbientLight(1, ambientLightColor));
                scene.set_background(color);
                scene.set_camera(camera);
                scene.set_distance(distance);
                scene.addGeometries(geometries);
                ImageWriter imageWriter = new ImageWriter("my_xml_image", width, height, nX, nY);
                Render render = new Render(imageWriter, scene);
                render.renderImage();
                render.printGrid(50, java.awt.Color.YELLOW);
                render.writeToImage();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

}
