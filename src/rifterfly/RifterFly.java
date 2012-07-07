package rifterfly;
//RifterFly

public class RifterFly {

    boolean running;

    Game game;
    Screen scr;

    public RifterFly() {
    	running = true;
		game = new Game(640,480);
		scr = new Screen("RifterFly",640,480,game.keys);
    }

    public void start() {
		double time = getTime();
		double oldTick = getTime();
		double oldBlit = getTime();
	
		while(running) {
		    time = getTime();
	
		    if(time - oldTick > 0.016) {
				if(game.tick(time-oldTick) != 0)
					running = false;
				oldTick = getTime();
		    }
	
		    if(time - oldBlit > 0.005) {
				scr.blit(game);
				oldBlit = getTime();
		    }
		    
		    
		}
    }

    public static double getTime() {
    	return System.nanoTime()/1000000000.0;
    }

    public static void main(String[] args) {
		RifterFly rf = new RifterFly();
		rf.start();
    }

}
