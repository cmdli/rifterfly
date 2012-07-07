package rifterfly;

import java.awt.Graphics2D;
//RifterFly
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;


public class Iteron extends Unit implements Enemy {

	public static final int w = 332;
	public static final int h = 50;
	
	vector loc;
	
	boolean tackled;
	
	public Iteron(double nx, double ny) {
		loc = new vector(nx,ny);
		tackled = false;
	}
	
	public int tick(double time, Game game) {
		
		if(tackled) {
			game.win = true;
			game.ended = true;
			
		}
		
		return 0;
	}

	public double getRot() {
		return 0.0;
	}

	public int getWidth() {
		return w;
	}

	public int getHeight() {
		return h;
	}

	public double getX() {
		return loc.x;
	}

	public double getY() {
		return loc.y;
	}

	public int tackle() {
		tackled = true;
		return 0 ;
	}

	public void paint(Graphics2D draw, Art art, Game game) {
		if(tackled) {
	    	short[] alpha = new short[256];
	    	short[] red = new short[256];
	    	short[] green = new short[256];
	    	short[] blue = new short[256];
	    	for(short i = 0; i < 256; i++) {
	    		alpha[i] = i;
	    		red[i] = 0;
	    		green[i] = 0;
	    		blue[i] = 200;
	    	}
	    	short[][] data = new short[][] {
	    			red, green, blue, alpha
	    	};
	    	ShortLookupTable table = new ShortLookupTable(0,data);
	    	LookupOp rop = new LookupOp(table,null);
	    	draw.drawImage(art.iteron, rop, (int)(getX()-game.screenXP),(int)getY());
    	}
    	else
    		draw.drawImage(art.iteron,(int)(getX()-game.screenXP),(int)getY(),null);
	}

	public Box[] getCollide() {
		Box[] bxs = new Box[1];
    	bxs[0] = new Box((int)loc.x,(int)loc.y,w,h);
     	return bxs;
	}
	
	
	
}
