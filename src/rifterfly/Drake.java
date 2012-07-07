package rifterfly;

import java.awt.Graphics2D;
//RifterFly

//w: 324
//h: 129

public class Drake extends Unit implements Enemy {

	public static final int w = 324;
	public static final int h = 129;
	public static final double LAUNCH_INTER = 1.25;
	public static final double START_LAUNCH = 1.0;
	public static final double MISSILE_SPEED = 500.0;
	public static final double MAX_BOSS_TIME = 30.0;
	
	vector loc;
	
	double[] launchers;
	
	boolean bossed;
	
	public Drake(double nx, double ny) {
		bossed = false;
		loc = new vector(nx,ny);
		launchers = new double[16];
		for(int i = 0; i < launchers.length; i++) {
			if(i < 8) {
				launchers[i] = i*LAUNCH_INTER + START_LAUNCH;
			} else {
				launchers[i] = (i%8)*LAUNCH_INTER + LAUNCH_INTER/2 + START_LAUNCH;
			}
		}
	}
	
	public int tick(double time, Game game) {
		
		if(bossed) {
			for(int i = 0; i < launchers.length;i++) {
				if(launchers[i] > 0.0)
					launchers[i] -= time;
				shoot(i,game);
			}
		}
		
		if(!bossed && (int)loc.x+w < (int)game.screenXP + game.w) {
			game.pause(MAX_BOSS_TIME);
			bossed = true;
		}
		
		if(loc.x + game.ENEMY_BUFFER + w < game.screenXP) {
			return 1;
		}
		
		
		return 0;
	}
	
	public void shoot(int i, Game game) {
		if(launchers[i] > 0.0)
			return;
		
		launchers[i] = 8.0*LAUNCH_INTER;
		vector offset = new vector();
		vector nvel = new vector();
		if(i < 8) {
			offset.x = i*13.875+108;
			nvel.y = -MISSILE_SPEED;
		}
		else {
			offset.x = i*13.875+108;
			offset.y = (double)h;
			nvel.y = MISSILE_SPEED;
		}
		Missile m1 = new Missile(loc.x+offset.x,loc.y+offset.y,nvel,new vector(game.pl.loc));
		game.enes.add(m1);
	}

    public void paint(Graphics2D draw, Art art, Game game) {
    	draw.drawImage(art.drake,(int)(loc.x-game.screenXP),(int)loc.y,null);
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
		Box[] bxs = new Box[4];
		bxs[0] = new Box((int)loc.x+114,(int)loc.y,126,h);
		bxs[1] = new Box((int)loc.x+12,(int)loc.y+29,300,13);
		bxs[2] = new Box((int)loc.x+12,(int)loc.y+86,300,13);
		bxs[3] = new Box((int)loc.x+30,(int)loc.y+17,252,95);
		return bxs;
	}

	public int tackle() {
		return 1;
	}
    
}
