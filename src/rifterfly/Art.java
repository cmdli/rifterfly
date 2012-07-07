//RifterFly

package rifterfly;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class Art {

    BufferedImage 
	rifter,
	erifter,
	caracal,
	drake,
	iteron,
	
	missile,
	drone,
	
	end,
	win,
	start;

    public Art() {
		rifter = loadImage("art/rifter.png");
		erifter = loadImage("art/erifter.png");
		caracal = loadImage("art/caracal.png");
		drake = loadImage("art/drake.png");
		missile = loadImage("art/missile.png");
		drone = loadImage("art/drone.png");
		end = loadImage("art/end.png");
		win = loadImage("art/win.png");
		iteron = loadImage("art/iteron.png");
		start = loadImage("art/start.png");
    }

    public static BufferedImage loadImage(String url) {
    	try {
    		BufferedImage src,img;
    		
    		img = ImageIO.read(new File(url));
    		src = new BufferedImage(img.getWidth(),img.getHeight(),BufferedImage.TYPE_INT_ARGB);
    		
    		
    		for(int y = 0; y < img.getHeight(); y++) {
    			for(int x = 0; x < img.getWidth(); x++) {
    				int bgr = img.getRGB(x, y);
    				if(bgr != -65281) {//Value for Transparency
    					src.setRGB(x, y, bgr | 0xFF000000);
    				}
    			}
    		}
    		
    		return src;
    	}
    	catch(Exception e) {
    		e.printStackTrace();
    		return null;
    	}
    }


}


