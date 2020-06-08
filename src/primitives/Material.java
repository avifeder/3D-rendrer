package primitives;


/**
 * Material class represents kind of stuff
 * @author avi && daniel
 */
public class Material {
    double _kD;
    double _kS;
    double _KT; //transparency
    double _KR; //reflection
    int _nShininess;

    /**
     * constructor of Material class
     * @param _kD gets the kD
     * @param _kS gets the kS
     * @param _nShininess gets the nShininess
     */
    public Material(double _kD, double _kS, int _nShininess) {
        this._kD = _kD;
        this._kS = _kS;
        this._nShininess = _nShininess;
    }
    /**
     * constructor of Material class
     * @param _kD gets the kD
     * @param _kS gets the kS
     * @param _KT gets the kT
     * @param _KR gets the KR
     * @param _nShininess gets the nShininess
     */
    public Material(double _kD, double _kS, int _nShininess, double _KT, double _KR) {
        this._kD = _kD;
        this._kS = _kS;
        this._KT = _KT;
        this._KR = _KR;
        this._nShininess = _nShininess;
    }

    /**
     * @return the kD
     */
    public double get_kD() {
        return _kD;
    }

    /**
     * @return the kS
     */
    public double get_kS() {
        return _kS;
    }

    /**
     * @return the nShininess
     */
    public int get_nShininess() {
        return _nShininess;
    }
    /**
     * @return the kt
     */
    public double get_KT() {
        return _KT;
    }
    /**
     * @return the kr
     */
    public double get_KR() {
        return _KR;
    }
}
