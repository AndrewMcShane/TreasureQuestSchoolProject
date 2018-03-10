import java.awt.Point;

public interface SearchStrategy {
	//get the name of the search strategy.
	public String getName();
	
	//return the point that the object should move to next.
	public Point move(Point shipLocation, Point currentLocation);
	
	
}
