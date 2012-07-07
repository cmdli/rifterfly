package rifterfly;
//RifterFly

import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Game {

	public static final double ENEMY_BUFFER = 100.0;
	public static final double SCREEN_SCROLL_BASE = 100.0;

	public double SCREEN_SCROLL = SCREEN_SCROLL_BASE;
	
    Rifter pl;
    Keys keys;
    Level lvl;

    ArrayList<Enemy> enes;

    int screenX;
    double screenXP;
    int w;
    int h;
    boolean ended;
    double pauseTime;
    boolean win;
    boolean started;

    public Game(int nw, int nh) {
		keys = new Keys();
		w = nw;
		h = nh;
		init();
    }
    
    public void init() {
    	enes = new ArrayList<Enemy>();
    	
		pl = new Rifter(25.0,(double)(h/2),this);
		lvl = new Level();
		lvl.loadFromImage("lvl/lvl1.png");
		screenXP = 0.0;
		screenX = 0;
		ended = false;
		win = false;
		pauseTime = 0.0;
		started = false;
    }

    public int tick(double time) {
    	
    	if(!started && keys.keys[KeyEvent.VK_SPACE]) {
    		started = true;
    		keys.keys[KeyEvent.VK_SPACE] = false;
    	}
    	
    	if(ended && keys.keys[KeyEvent.VK_SPACE]) {
    		init();
    		return 0;
    	}
    	if(ended && keys.keys[KeyEvent.VK_ESCAPE])
    		return 1;
    	
    	if(ended || !started)
    		return 0;
    	
    	if(pauseTime > 0.0)
    		pauseTime -= time;
    	
    	if(pl.tick(time,this) != 0)
    		ended = true;
    	
    	lvl.checkEnemies(this,(int)screenXP,w);
    	
    	//Not using the enhanced For loops because
    	//of weird concurrency errors (there is only
    	//one thread)
    	for(int i = 0; i < enes.size(); i++) {
    		if(enes.get(i).tick(time, this) != 0)
    			enes.remove(i);
    	}
    	
    	if(pauseTime <= 0.0) {
	    	double scroll = SCREEN_SCROLL*time;
	        screenXP += scroll;
	        screenX = (int)screenXP;
    	}
        
        return 0;
    }
    
    public void pause(double time) {
    	pauseTime = time;
    }
    

}
