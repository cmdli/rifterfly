package rifterfly;
//RifterFly

public class vector {
    double x;
    double y;
    public vector() {
    	x = 0.0; y = 0.0;
    }
    public vector(double nx, double ny) {
    	x = nx;
    	y = ny;
    }
    public vector(vector src) {
    	x = src.x;
    	y = src.y;
    }
}
