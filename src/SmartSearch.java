import java.awt.Point;

public class SmartSearch implements SearchStrategy {
	
	private String name;
	
	public SmartSearch(){
		this.name = "Smart Search";
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		/*
		 * SmartSearch will find the shortest path 
		 * to the user ship and make the first step 
		 * towards it each time. This will likely
		 * be the highest costing search method.
		 */
		return null;
	}

}
