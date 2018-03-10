import java.awt.Point;

public interface Monster {
	//get the name of the monster
	public String getName();
	
	//get the location of the monster on the map
	public Point getLocation();
	
	//get the location of the user ship on the map
	public Point getSubjectLocation();
	
	//get the search strategy used by this monster
	public SearchStrategy getSearchStrategy();
	
	//set the search strategy used by this monster
	public void setSearchStrategy(SearchStrategy strategy);
	
}
