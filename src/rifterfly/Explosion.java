package rifterfly;
import java.awt.Color;
import java.awt.Graphics2D;

//RifterFly
public class Explosion extends Unit implements Enemy {

	public static final int w = 30;
	public static final int h = w;
	
	vector loc;
	double timeElapsed;
	boolean red;
	int colorTimer;
    double damage;
    boolean used;

    public Explosion(double nx, double ny) {
    	this(nx,ny,0.0);
    }
	
    public Explosion(double nx, double ny, double ndamage) {
		loc = new vector(nx,ny);
		timeElapsed = 0.0;
		red = true;
		colorTimer = 0;
		damage = ndamage;
		used = false;
	}

	public int tick(double time, Game game) {
		timeElapsed += time;
		
		if(timeElapsed >= 5.0) {
			return 1;
		}

		if(!used && collide(this,game.pl)) {
		    game.pl.damage(damage);
		    used = true;
		}
		
		colorTimer++;
		if(colorTimer % 5 == 0)
			red = !red;
		
		return 0;
	}

    public void paint(Graphics2D draw, Art art, Game game) {
		draw.setColor(getColor());
		draw.drawOval((int)getX()-getWidth()/2-(int)game.screenXP, (int)getY()-getHeight()/2, getWidth(), getHeight());  
    }
	
	public Color getColor() {
		if(red)
			return Color.red;
		return Color.orange;
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
		bxs[0] = new Box((int)loc.x + (int)(w*0.1), (int)loc.y + (int)(h*0.1), (int)(w*0.8), (int)(h*0.8));
		return bxs;
	}
	
	public int tackle() {
		return 1;
	}
	
}
