package unittests;

import org.junit.Test;
import primitives.Color;
import renderer.ImageWriter;

import static org.junit.Assert.*;

/**
 * Unit tests for renderer.ImageWriterTest class
 * @author avi && daniel
 */
public class ImageWriterTest {


    /**
     * Test for creating image
     * Create image with a grid of 10x16 squares on the screen (ViewPlane) 1000 x 1600 and a resolution of 500 x 800:
     * Test method for {@link renderer.ImageWriter#writePixel(int, int, java.awt.Color)}
     * Test method for {@link renderer.ImageWriter#ImageWriter(String, double, double, int, int)}
     * Test method for {@link renderer.ImageWriter#writeToImage()}
     */
    @Test
    public void ImageWriterTest() {
        ImageWriter imageWriter = new ImageWriter("my_image", 1600,1000, 800,500);
        Color color = new Color(0,191,255);
        for(int x = 0; x < 800; x++)
        {
            for(int y = 0; y < 500; y++)
            {
                if(x % 50 == 0 || y % 50 == 0 || x == 799 || y ==499)
                    imageWriter.writePixel(x , y , Color.BLACK.getColor());
                else
                    imageWriter.writePixel(x , y , color.getColor());
            }
        }
        imageWriter.writeToImage();
    }
}