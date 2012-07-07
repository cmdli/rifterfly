package rifterfly;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;

//RifterFly

public class Level {

    public static final int ACT_BUFFER = 220;
    public static final int RES_SIZE = 20;

    HashMap<Integer,ArrayList<Enemy>> enes;

    int prev;

    public Level() {
    	prev = 0;
    	enes = new HashMap<Integer,ArrayList<Enemy>>();
    }

    //Loads Enemies into the Game from the Level data
    public void checkEnemies(Game game, int x, int w) {
	
		ArrayList<Enemy> tmp;
	
		for(int i = prev; i < x + w + ACT_BUFFER; i++) {
		    if((tmp = enes.get(i)) != null) {
		    	for(Enemy e : tmp)
		    		game.enes.add(e);
		    }
		}
	
		prev = x + w + ACT_BUFFER;
    }
  
    //Loads the level data from an image
    public void loadFromImage(String name) {
    	try {
    		BufferedImage img = ImageIO.read(new File(name));
    		int[] pixels = new int[img.getWidth()*img.getHeight()];
    		img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
    		
    		for(int x = 0; x < img.getWidth(); x++) {
    			for(int y = 0; y < img.getHeight(); y++) {
    				int col = pixels[x+y*img.getWidth()];
    				switch(col) {
    				//Rifter
    				case 0xFFFF0000:
    					addEnemy(0,x,y);
    					//Clear used Pixels
    					for(int dx = 0; dx < 3; dx++) {
    						for(int dy = 0; dy < 2; dy++) {
    							pixels[x+dx+(y+dy)*img.getWidth()] = 0x00000000;
    						}
    					}
    					break;
    				//Caracal
    				case 0xFF0000FF:
    					addEnemy(1,x,y);
    					//Clear used Pixels
    					for(int dx = 0; dx < 7; dx++) {
							for(int dy = 0; dy < 5; dy++) {
								pixels[x+dx+(y+dy)*img.getWidth()] = 0x00000000;
							}
    					}
    					break;
    				//Drake
    				case 0xFF00FF00:
    					addEnemy(2,x,y);
    					//Clear used Pixels
    					for(int dx = 0; dx < 16; dx++) {
    						for(int dy = 0; dy < 5; dy++) {
    							pixels[x+dx+(y+dy)*img.getWidth()] = 0x00000000;
    						}
    					}
    					break;
    				//Iteron
    				case 0xFFFF8000:
    					addEnemy(3,x,y);
    					for(int dx = 0; dx < 16; dx++) {
    						for(int dy = 0; dy < 3; dy++) {
    							pixels[x+dx+(y+dy)*img.getWidth()] = 0x00000000;
    						}
    					}
    				}
    			}
    		}
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    	}
    }
    
    //Adds an Enemy to the data based on type and location
    public void addEnemy(int type, int x, int y) {
    	Enemy e = null;
    	int lx = RES_SIZE*x;
    	int ly = RES_SIZE*y;
    	switch(type) {
    	//Rifter
    	case 0:
    		e = new ERifter((double)lx,(double)ly);
    		break;
    	//Caracal
    	case 1:
    		e = new Caracal((double)lx,(double)ly);
    		break;
    	//Drake
    	case 2:
    		e = new Drake((double)lx,(double)ly);
    		break;
    	//Iteron
    	case 3:
    		e = new Iteron((double)lx,(double)ly);
    		break;
    	}
    	ArrayList<Enemy> tmpEnes = null;
    	if((tmpEnes = enes.get(lx)) != null) {
    		tmpEnes.add(e);
    		return;
    	}
    	tmpEnes = new ArrayList<Enemy>();
    	tmpEnes.add(e);
    	enes.put(lx, tmpEnes);
    }
}