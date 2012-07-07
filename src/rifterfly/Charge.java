package rifterfly;

import java.awt.Color;
//RifterFly
import java.awt.Graphics2D;

public class Charge extends Unit implements Enemy {

    public static final int w = 3;
    public static final int h = 3;
    public static final double DAMAGE = 25.0;

    vector loc;
    vector vel;

    public Charge(double nx, double ny, vector nvel) {
        loc = new vector(nx,ny);
		vel = nvel;
    }
	
    public int tick(double time, Game game) {
	
		if(collide(this,game.pl)) {
		    game.pl.damage(DAMAGE);
		    return 1;
		}
		
		loc.x += vel.x*time;
		loc.y += vel.y*time;
		
		if(loc.x + game.ENEMY_BUFFER + w < game.screenXP) {
		    return 1;
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

    public void paint(Graphics2D draw, Art art, Game game) {
		draw.setColor(Color.yellow);
		draw.fillRect((int)(loc.x-game.screenXP),(int)loc.y,w,h);
    }

    public Box[] getCollide() {
		Box[] bxs = new Box[1];
		bxs[0] = new Box((int)loc.x,(int)loc.y,w,h);
		return bxs;
    }

	public int tackle() {
		return 1;
	}


}
