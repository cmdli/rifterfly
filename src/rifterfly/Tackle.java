package rifterfly;
import java.awt.Color;
import java.awt.Graphics2D;

//RifterFly

public class Tackle extends Unit implements Enemy {
	
	public static final int w = 5;
	public int h = 5;

    public static final double SHOOT_X = 500.0;
    public static final double SHOOT_Y = 50.0;
	public static final double TACKLE_GROWTH = (double)(2*SHOOT_Y);
	
	vector loc;
	vector vel;
	double nextH;
	
	public Tackle(double nx, double ny, Game g) {
		loc = new vector(nx,ny);
		vel = new vector(SHOOT_X+g.SCREEN_SCROLL,-SHOOT_Y);
		nextH = (double)h;
	}
	
	public int tick(double time,Game game) {
		nextH += TACKLE_GROWTH*time;
		h  = (int)(nextH);
		
		if(loc.x > game.screenXP + game.w + 50.0)
			return 1;
		
		loc.x += vel.x*time;
		loc.y += vel.y*time;
		
		//Collision Detection
		for(int i = 0; i < game.enes.size(); i++){
			Enemy e = game.enes.get(i);
			if(e instanceof Unit) {
				Unit u = (Unit)e;
				if(collide(this,u)) {
					if(e.tackle() == 0)
						return 1;
				}
			}
		}
		
		return 0;
	}

	public void paint(Graphics2D draw, Art art, Game game) {
		draw.setColor(Color.black);
		draw.fillRect((int)(loc.x-game.screenXP), (int)loc.y, w, h);
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

	public Box[] getCollide() {
		Box[] bxs = new Box[1];
		bxs[0] = new Box((int)loc.x,(int)loc.y,w,h);
		return bxs;
	}

	public int tackle() {
		return 1;
	}
	
}
