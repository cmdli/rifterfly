package rifterfly;

import java.awt.Graphics2D;
//RifterFly

public interface Enemy {

    public int tick(double time, Game game);
    
    public double getRot();
    public int getWidth();
    public int getHeight();
    public double getX();
    public double getY();
    
    //Tackle method, returns 0 if tackle wave is destroyed, non-zero otherwise
    public int tackle();

    public void paint(Graphics2D draw, Art art, Game game);
    
}
