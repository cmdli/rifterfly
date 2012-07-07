package rifterfly;

import java.awt.Graphics2D;
import java.awt.image.LookupOp;
import java.awt.image.ShortLookupTable;

public class ERifter extends Unit implements Enemy {

    public static final int w = 62;
    public static final int h = 42;
    public static final double BULLET_INTER = 2.0;
    public static final double X_SPEED = -1.0;
    public static final double BULLET_SPEED = -100.0;

    double bulletTime;
    vector loc;
    vector vel;
    boolean tackled;

    public ERifter(double x, double y) {
    	loc = new vector(x,y);
		bulletTime = 0.0;
		tackled = false;
    }

    public int tick(double time, Game game) {
    	if(tackled)
    		return 0;
    	
		if(bulletTime > 0.0)
		    bulletTime -= time;

		loc.x += (int)(X_SPEED*time);
		shoot(game);
		
		if(loc.x + game.ENEMY_BUFFER + w < game.screenXP) {
			return 1;
		}
		return 0;
    }

    public void shoot(Game game) {
		if(bulletTime > 0.0) 
		    return;
		
		vector vel = new vector(X_SPEED+BULLET_SPEED,0.0);
		
		Charge c = new Charge(loc.x+w*3/4,loc.y-5.0,vel);
		game.enes.add(c);
		
		c = new Charge(loc.x+w*3/4,loc.y+h+5.0,vel);
		game.enes.add(c);
		
		c = new Charge(loc.x+w/2,loc.y+5.0,vel);
		game.enes.add(c);
		
		c = new Charge(loc.x+w/2,loc.y+h-5.0,vel);
		game.enes.add(c);

		bulletTime = BULLET_INTER;
    }

    public void paint(Graphics2D draw, Art art, Game game) {
    	if(tackled) {
	    	short[] alpha = new short[256];
	    	short[] red = new short[256];
	    	short[] green = new short[256];
	    	short[] blue = new short[256];
	    	for(short i = 0; i < 256; i++) {
	    		alpha[i] = i;
	    		red[i] = (short)(i*.5);
	    		green[i] = 0;
	    		blue[i] = 200;
	    	}
	    	short[][] data = new short[][] {
	    			red, green, blue, alpha
	    	};
	    	ShortLookupTable table = new ShortLookupTable(0,data);
	    	LookupOp rop = new LookupOp(table,null);
	    	draw.drawImage(art.erifter, rop, (int)(getX()-game.screenXP),(int)getY());
    	}
    	else
    		draw.drawImage(art.erifter,(int)(getX()-game.screenXP),(int)getY(),null);
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

	
	public double getRot() {
		return 0.0;
	}

	public Box[] getCollide() {
		Box[] bxs = new Box[2];
    	bxs[0] = new Box((int)loc.x+53,(int)loc.y,8,h);
    	bxs[1] = new Box((int)loc.x+4,(int)loc.y+10,w-6,21);
    	return bxs;
	}

	public int tackle() {
		tackled = true;
		return 0;
	}

}
