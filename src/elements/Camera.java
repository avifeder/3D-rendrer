package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


/**
 * Camera class
 * represents Vector with a final point
 * @author avi && daniel
 */
public class Camera {
    Point3D _location;
    Vector _Vup;
    Vector _Vto;
    Vector _Vright;

    /**
     * contractor
     * @param location the center point
     * @param Vto the x vector
     * @param Vup the y vector
     */
    public Camera(Point3D location, Vector Vto, Vector Vup) throws Exception {
        if (Vto.dotProduct(Vup)!=0)
            throw new IllegalArgumentException("the vectors must be orthogonal");
         this._location=location;
        this._Vup=Vup.normalize();
        this._Vto=Vto.normalize();
        this. _Vright=_Vup.crossProduct(_Vto).normalize();
    }

    /**
     * getLocation
     * @return the center point
     */
    public Point3D getLocation() {
        return _location;
    }

    /**
     * getVup
     * @return the y vector
     */
    public Vector getVup() {
        return _Vup;
    }

    /**
     * getVto
     * @return the x vector
     */
    public Vector getVto() {
        return _Vto;
    }

    /**
     * getVright
     * @return the z vector
     */
    public Vector getVright() {
        return _Vright;
    }

    /**
     * constructRayThroughPixel - find the ray within the camera and pixel
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j the wanted pixel coordinate
     * @param i the wanted pixel coordinate
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth the view plane width
     * @param screenHeight the view plane height
     * @return the ray within the camera and pixel
     */
    public Ray constructRayThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight) throws Exception {
        Point3D Pc;
        if (screenDistance != 0)
            Pc = this._location.add(this._Vto.scale(screenDistance));
        else
            Pc = this._location;
        Point3D Pij = Pc;
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;
        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;
        if(Xj != 0) Pij = Pij.add(this._Vright.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(this._Vup.scale(-Yi)) ;
        return new Ray(this._location, Pij.subtract(this._location));
    }

    /**
     * constructRayThroughPixel - find the ray within the camera and pixel
     * @param nX number of pixels in x axis
     * @param nY number of pixels in y axis
     * @param j the wanted pixel coordinate
     * @param i the wanted pixel coordinate
     * @param screenDistance the distance between the camera and the view plane
     * @param screenWidth the view plane width
     * @param screenHeight the view plane height
     * @param numOfRays the number of rays for each pixel
     * @return list of rays for pixel
     */
    public List<Ray> constructRaysThroughPixel (int nX, int nY, int j, int i, double screenDistance, double screenWidth, double screenHeight, int numOfRays) throws Exception {
        int numOfRaysInRowCol = (int)Math.ceil(Math.sqrt(numOfRays));
        if(numOfRaysInRowCol == 1)
            return List.of(constructRayThroughPixel(nX, nY, j, i, screenDistance, screenWidth,screenHeight));
        Point3D Pc;
        if (screenDistance != 0)
            Pc = this._location.add(this._Vto.scale(screenDistance));
        else
            Pc = this._location;
        Point3D Pij = Pc;
        double Ry = screenHeight/nY;
        double Rx = screenWidth/nX;
        double Yi= (i - (nY/2d))*Ry + Ry/2d;
        double Xj= (j - (nX/2d))*Rx + Rx/2d;
        if(Xj != 0) Pij = Pij.add(this._Vright.scale(-Xj)) ;
        if(Yi != 0) Pij = Pij.add(this._Vup.scale(-Yi)) ;

        double PRy = Ry/numOfRaysInRowCol;
        double PRx = Rx/numOfRaysInRowCol;
        List rays = new LinkedList<Ray>();

        //the center of the pixel
        Point3D tempPij = Pij;

        //Split the pixel to a grid
        for (int r = 0; r < numOfRaysInRowCol; r++){
            double PXj= (r - (numOfRaysInRowCol/2d))*PRx + PRx/2d;
            for(int c = 0; c < numOfRaysInRowCol; c++)
            {
                double PYi= (c - (numOfRaysInRowCol/2d))*PRy + PRy/2d;
                if(PXj != 0) Pij = Pij.add(this._Vright.scale(-PXj)) ;
                if(PYi != 0) Pij = Pij.add(this._Vup.scale(-PYi)) ;
                rays.add(new Ray(this._location, Pij.subtract(this._location)));
                Pij = tempPij;
            }
        }
        return rays;
    }
}
