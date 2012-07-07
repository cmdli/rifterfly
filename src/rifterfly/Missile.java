package rifterfly;
import java.awt.Color;
import java.awt.Graphics2D;

//RifterFly

public class Missile extends Unit implements Enemy {

    public static final double ACCEL = 2000.0;
    public static final double MAX_SPEED = 400.0;
    public static final double DAMAGE = 100.0;
    public static final int w = 5;
    public static final int h = 5;

    vector tarloc; //Target Location
    vector loc;
    vector vel;
    boolean tackled;

    public Missile(double nx, double ny, vector nvel, vector ntarloc) {
    	loc = new vector(nx,ny);
		vel = nvel;
		tarloc = ntarloc;
		tackled = false;
    }

    public int tick(double time, Game game) {
	    if(!tackled) {
			double angle = Math.atan2(tarloc.y - loc.y,tarloc.x - loc.x);
			vector acc = new vector();
			acc.x = ACCEL*Math.cos(angle);
			acc.y = ACCEL*Math.sin(angle);
			vel.x += acc.x*time;
			vel.y += acc.y*time;
		
			double speed = Math.sqrt(vel.x*vel.x + vel.y*vel.y);
			if(speed > MAX_SPEED) {
				vel.x *= MAX_SPEED/speed;
				vel.y *= MAX_SPEED/speed;
			}
	    }
		
		loc.x += vel.x*time;
		loc.y += vel.y*time;
		
		if(Math.abs(loc.x - tarloc.x) < 15.0
				&& Math.abs(loc.y - tarloc.y) < 15.0) {
		    game.enes.add(new Explosion((int)loc.x,(int)loc.y,DAMAGE));
			return 1;
		}
		
		return 0;
    }

    public void paint(Graphics2D draw, Art art, Game game) {
		double angle = getRot();
		int x = (int)(getX()-game.screenXP);
		int y = (int)getY();
		draw.rotate(angle,x,y);
		draw.drawImage(art.missile,x,y,null);
		draw.rotate(-angle,x,y);
    }

    public double getRot() {
    	return Math.atan2(vel.y,vel.x);
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

	public Box[] getCollide() {
		Box[] bxs = new Box[1];
		bxs[0] = new Box((int)loc.x,(int)loc.y,w,h);
		return null;
	}

	public int tackle() {
		tackled = true;
		return 1;
	}
}
