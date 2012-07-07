package rifterfly;
//RifterFly

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorConvertOp;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;
import java.util.ArrayList;

//w:145
//h:92

public class Caracal extends Unit implements Enemy {

    private static final int w = 145;
    private static final int h = 92;
    public static final double DRONE_INTER = 3.0;
    

    vector loc;
    double droneTime;
    int numDrones;
    boolean tackled;

    public Caracal(double nx, double ny) {
		loc = new vector(nx,ny);
		droneTime = DRONE_INTER;
		numDrones = 0;
		tackled = false;
    }

    public int tick(double time, Game game) {
		if(droneTime > 0.0)
		    droneTime -= time;
	
		if(numDrones < 4)
			createDrones(game);
		
		if(loc.x + game.ENEMY_BUFFER + w < game.screenXP) {
			return 1;
		}
		return 0;
    }

    private void createDrones(Game game) {
		if(droneTime > 0.0)
		    return;
	
		Drone d1, d2;
		d1 = new Drone(loc.x + 50, loc.y - 10);
		d2 = new Drone(loc.x + 50, loc.y + h + 10);
		game.enes.add(d1);
		game.enes.add(d2);
		
		numDrones += 2;
		droneTime = DRONE_INTER;
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
	    	draw.drawImage(art.caracal, rop, (int)(getX()-game.screenXP),(int)getY());
    	}
    	else
    		draw.drawImage(art.caracal,(int)(getX()-game.screenXP),(int)getY(),null);
    }
    
    public double getX() {
    	return loc.x;
    }
    
    public double getY() {
    	return loc.y;
    }

    public int getWidth() {
    	return w;
    }

    public int getHeight() {
    	return h;
    }

	public double getRot() {
		return 0.0;
	}

	public Box[] getCollide() {
		Box[] bxs = new Box[4];
    	bxs[0] = new Box((int)loc.x,(int)loc.y+40,w,11);
    	bxs[1] = new Box((int)loc.x+100,(int)loc.y,16,h);
    	bxs[2] = new Box((int)loc.x+105,(int)loc.y+12,26,67);
    	bxs[3] = new Box((int)loc.x+132,(int)loc.y+36,16,20);
    	return bxs;
	}

	public int tackle() { 
		tackled = true;
		return 0;
	}

}
