import java.awt.Point;
import java.util.Observable;
import java.util.Observer;

public class GiantSquidMonster implements Monster, Observer {
	
	private String name;
	private Point currentLocation;
	private Point subjectLocation;
	SearchStrategy searchStrategy;
	
	public GiantSquidMonster(){
		this.name = "Giant Squid";
		this.searchStrategy = new IdleSearch();
		this.subjectLocation = OceanMap.getInstance().getShipLocation();
		this.currentLocation = OceanMap.getInstance().placeMonster();
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point getLocation() {
		return this.currentLocation;
	}

	@Override
	public Point getSubjectLocation() {
		return this.subjectLocation;
	}

	@Override
	public SearchStrategy getSearchStrategy() {
		return this.searchStrategy;
	}

	@Override
	public void setSearchStrategy(SearchStrategy strategy) {
		this.searchStrategy = strategy;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof Ship){
			subjectLocation = ((Ship) o).getLocation();
		}
		
		currentLocation = searchStrategy.move(subjectLocation, currentLocation);
		//TODO: code for checking if the monster reached the player, in which case the game is lost.
		if(currentLocation.getX() == subjectLocation.getX() && currentLocation.getY() == subjectLocation.getY()){
			OceanMap.getInstance().gameLose();
		}
		
	}

}
