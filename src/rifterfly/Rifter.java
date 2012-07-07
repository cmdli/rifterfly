package rifterfly;
import java.awt.event.KeyEvent;

//RifterFly

public class Rifter extends Unit {

    public static final double SPEED_BASE = 150.0;
    
    public static final double SHOOT_INTER = 1.0;
    
    public static final double MAX_SHIELD = 100.0;
    public static final double SHIELD_REGEN = 10.0;
    public static final double MAX_ARMOR = 500.0;
    public static final double MAX_STRUCTURE = 100.0;
    public static final double MAX_ENERGY = 100.0;
    public static final double ENERGY_REGEN = 5.0;
    public static final double TACKLE_ENERGY = 50.0;
    public static final double WARP_ENERGY = 10.0;
    
    public static final int w = 62;
    public static final int h = 42;

    private Game game;
    
    vector vel;
    vector loc;
    
    double shields;
    double armor;
    double structure;
    double energy;
    double speed;
    
    double shootTime;

    public Rifter(double nx, double ny, Game ngame) {
		vel = new vector();
		loc = new vector();
		loc.x = nx;
		loc.y = ny;
		game = ngame;
		shields = MAX_SHIELD;
		armor = MAX_ARMOR;
		structure  = MAX_STRUCTURE;
		energy = MAX_ENERGY;
    }

    public int tick(double time, Game g) {
    	
    	if(structure < 0.0) {
    		g.enes.add(new Explosion(loc.x,loc.y));
    		return 1;
    	}
    	
    	if(shootTime > 0.0)
    		shootTime -= time;
    	
    	//Shield Regen
    	if(shields < MAX_SHIELD) {
    		shields += SHIELD_REGEN*time;
    		if(shields > MAX_SHIELD)
    			shields = MAX_SHIELD;
    	}
    	
    	//Key Input
		boolean[] keys = g.keys.keys;
		vel.x = 0.0;
		vel.y = 0.0;
		if(keys[KeyEvent.VK_H]) {
			speed = 1.5*SPEED_BASE;
			energy -= WARP_ENERGY*time;
		}
		else {
			speed = SPEED_BASE;
			g.SCREEN_SCROLL = g.SCREEN_SCROLL_BASE;
			if(energy < MAX_ENERGY) {
				energy += ENERGY_REGEN*time;
				if(energy > MAX_ENERGY)
					energy = MAX_ENERGY;
			}
		}
		if(keys[KeyEvent.VK_W]) {
		    vel.y -= speed;
		}
		if(keys[KeyEvent.VK_A]) {
		    vel.x -= speed;
		}
		if(keys[KeyEvent.VK_S]) {
		    vel.y += speed;
		}
		if(keys[KeyEvent.VK_D]) {
		    vel.x += speed;
		}
		if(keys[KeyEvent.VK_SPACE]) {
			shoot(g);
		}
		
		if(game.pauseTime <= 0.0){
		//Scrolling
		if(keys[KeyEvent.VK_J])
			vel.x+= 4.0*game.SCREEN_SCROLL;
		else
			vel.x += game.SCREEN_SCROLL;
		}
		
		//Collision Detection
		for(Enemy e : game.enes) {
			//Dont Collide
			if(e instanceof Drone)
				continue;
			if(e instanceof Explosion)
				continue;
			if(e instanceof Missile)
				continue;
			
			if(e instanceof Unit) {
				Unit u = (Unit)e;
				if(collide(this,u)) {
					stopDir(u);
				}
			}
		}
		
		loc.x += vel.x*time;
		loc.y += vel.y*time;
	
		//Bounds check
		if(loc.x + (double)w > g.screenXP + (double)g.w) {
		    loc.x = g.screenXP + (double)g.w - (double)w;
		}
		if(loc.y < 0.0)
		    loc.y = 0.0;
		if(loc.y + (double)h > (double)g.h)
		    loc.y = g.h - h;
		
		return 0;
		
    }
    
    public void shoot(Game g) {
    	
    	if(shootTime > 0.0)
    		return;
    	Tackle t = new Tackle(loc.x+(double)w,loc.y+(double)h/2,g);
    	g.enes.add(t);
    	shootTime = SHOOT_INTER;
    	energy -= TACKLE_ENERGY;
    }

    public void damage(double damage) {
    	
		shields -= damage;
		if(shields < 0.0) {
		    armor += shields;
		    shields = 0.0;
		}
		if(armor < 0.0) {
		    structure += armor;
		    armor = 0.0;
		}
    }
    
    public void stopDir(Unit u) {
    	Enemy e = (Enemy)u;
    	if(e.getY() < loc.y){
    		vel.y = Math.max(vel.y, 0.0);
    	}
    	else {
    		vel.y = Math.min(vel.y, 0.0);
    	}
    	if(e.getX() < loc.x) {
    		vel.x = Math.max(vel.x, 0.0);
    	}
    	else {
    		vel.x = Math.min(vel.x,0.0);
    	}
    }
    
    public Box[] getCollide() {
    	Box[] bxs = new Box[2];
    	bxs[0] = new Box((int)loc.x+3,(int)loc.y,6,h);
    	bxs[1] = new Box((int)loc.x+3,(int)loc.y+10,w-6,21);
    	return bxs;
    }

}
