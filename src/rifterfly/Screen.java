package rifterfly;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageFilter;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

//RifterFly

public class Screen extends JFrame {

    Art art;

    BufferedImage rifter, caracal, drake, erifter, missile, drone;
    BufferedImage buffer;
    Graphics2D draw;
    Graphics drawToScreen;
    int w;
    int h;

    public Screen(String title, int nw, int nh, Keys keys) {
		super(title);
		w = nw;
		h = nh+25;
		setSize(w,h);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(keys);
	
	
		buffer = new BufferedImage(nw,nh,BufferedImage.TYPE_INT_ARGB);
		draw = buffer.createGraphics();
		drawToScreen = getGraphics();

		art = new Art();
    }

    public void blit(Game game) {
	
		draw.setColor(Color.gray);
		draw.fillRect(0,0,w,h);
	
		if(!game.ended) {
			draw.drawImage(art.rifter, (int)(game.pl.loc.x-game.screenXP), (int)game.pl.loc.y,null);
		}
		
		for(Enemy e : game.enes){
		    e.paint(draw,art,game);
		}
		
		drawBar(10, 10, 50, 15, game.pl.energy,		game.pl.MAX_ENERGY,		Color.white);
		drawBar(10, 30, 50, 10, game.pl.shields,	game.pl.MAX_SHIELD,		Color.blue);
		drawBar(10, 45, 50, 10, game.pl.armor,		game.pl.MAX_ARMOR,		Color.black);
		drawBar(10, 60, 50, 10, game.pl.structure,	game.pl.MAX_STRUCTURE,	Color.ORANGE);
		
		if(!game.started)
			draw.drawImage(art.start, w/4, h/4, null);
		else if(game.win)
			draw.drawImage(art.win,w/4,h/4,null);
		else if(game.ended)
			draw.drawImage(art.end,w/4,h/4,null);
	
		drawToScreen.drawImage(buffer,0,22,null);
    }
    
    public void drawBar(int x, int y, int w, int h, double val, double max, Color c) {
    	draw.setColor(c);
    	double ratio = val/max;
    	draw.fillRect(x, y, (int)(w*ratio), h);
    	draw.setColor(Color.red);
    	draw.fillRect(x+(int)(w*ratio), y, w-(int)(w*ratio), h);
    }
    

}
