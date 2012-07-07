package rifterfly;

import java.awt.Graphics2D;
//RifterFly

public class Drone extends Unit implements Enemy {

    public static final int w = 18;
    public static final int h = 14;
    public static final double ACCEL = 200.0;
    public static final double MAX_SPEED = 200.0;
    public static final double DAMAGE = 100.0;
    public static final double MAX_TIME = 10.0;
    
    vector vel;
    vector loc;
    double droneTime;
    boolean tackled;

    public Drone(double nx, double ny) {
		vel = new vector();
		loc = new vector(nx,ny);
		droneTime = MAX_TIME;
		tackled = false;
    }

    public int tick(double time, Game game) {
    	if(droneTime > 0.0)
    		droneTime -= time;
    	else {
    		game.enes.add(new Explosion(loc.x,loc.y,DAMAGE));
    		return 1;
    	}
    	
    	goTowardsOrAway(game.pl.loc.x-loc.x,game.pl.loc.y-loc.y,time,true);
    	
    	//Check Collision with Drones
    	for(Enemy e : game.enes) {
    		if(e instanceof Drone) {
    			Drone d = (Drone)e;
    			if(d != this && collide(this,d)) {
    				goTowardsOrAway(d.loc.x-loc.x,d.loc.y-loc.y,time,false);
    			}
    		}
    			
    	}

		if(tackled || collide(this,game.pl)) {
		    game.enes.add(new Explosion(loc.x,loc.y,DAMAGE));
		    return 1;
		}
	    	
		loc.x += vel.x*time;
		loc.y += vel.y*time;
		return 0;
    }

    private void goTowardsOrAway(double dx, double dy, double time, boolean towards) {
    	vector acc = new vector();
		double angle = Math.atan2(dy,dx);
		acc.x = ACCEL*Math.cos(angle);
		acc.y = ACCEL*Math.sin(angle);
		//If going away, reverse
		if(!towards) {
			acc.x *= -1.0;
			acc.y *= -1.0;
		}
		vel.x += acc.x*time;
		vel.y += acc.y*time;
		double speed = Math.sqrt(vel.x*vel.x + vel.y*vel.y);
		if(speed >= MAX_SPEED) {
			vel.x *= MAX_SPEED/speed;
			vel.y *= MAX_SPEED/speed;
		}
    }

    public void paint(Graphics2D draw, Art art, Game game) {
		double angle = getRot();
		int x = (int)(getX()-game.screenXP);
		int y = (int)getY();
		draw.rotate(angle,x,y);
		draw.drawImage(art.drone,x,y,null);
		draw.rotate(-angle,x,y);
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
		return bxs;
	}

	public double getRot() {
		return Math.atan2(vel.y,vel.x);
	}

	public int tackle() {
		tackled = true;
		return 1;
	}

}
