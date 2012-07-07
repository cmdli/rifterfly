package rifterfly;
//RifterFly

public abstract class Unit {

    public boolean collide(Unit u1, Unit u2) {
    	return collide(u1.getCollide(),u2.getCollide());
    }

    public boolean collide(Box[] bs1, Box[] bs2) {
		for(Box b1 : bs1) {
		    for(Box b2 : bs2) {
				if(collide(b1,b2)) {
				    return true;
				}
		    }
		}
		return false;
    }

    public boolean collide(Box b1, Box b2) {
		if(b1.x+b1.w < b2.x)
		    return false;
		if(b1.y+b1.h < b2.y)
		    return false;
		if(b1.x > b2.x + b2.w)
		    return false;
		if(b1.y > b2.y + b2.h)
		    return false;
		return true;
    }

    public abstract Box[] getCollide();

}