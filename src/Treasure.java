import java.awt.Point;

public class Treasure {

	Point location;
	
	public Treasure(){
		this.location = OceanMap.getInstance().placeTreasure();
	}
	
	public Point getLocation(){
		return this.location;
	}
	
}
