import java.awt.Point;

public class IdleSearch implements SearchStrategy {

	private String name;
	
	public IdleSearch(){
		this.name = "Idle Search";
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		/*
		 * Idle search can function as an idle state,
		 * and a base state for all monster objects.
		 */
		return currentLocation;	
		
	}

}
