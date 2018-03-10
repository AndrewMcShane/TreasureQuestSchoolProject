import java.awt.Point;
import java.util.Random;

public class RandomSearch implements SearchStrategy {

	private String name;
	private Random rand  = new Random();
	
	public RandomSearch(){
		this.name = "Random Search";
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		/*
		 * will pick a random direction to move each time,
		 * including not moving at all.
		 */
		
		int nextMove = rand.nextInt(5);
		
		switch(nextMove){
		case 0:
			return currentLocation;
		case 1:
			return goEast(currentLocation);
		case 2:
			return goWest(currentLocation);
		case 3:
			return goSouth(currentLocation);
		case 4:
			return goNorth(currentLocation);
		default:
			return currentLocation;
		}
			
	}

    public Point goEast(Point currentLocation){
    	if(currentLocation.x<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x+1, currentLocation.y)){
    		return new Point(currentLocation.x+1, currentLocation.y);
    	}
    	return currentLocation;
    }
    
    public Point goWest(Point currentLocation){
    	if(currentLocation.x >0 && OceanMap.getInstance().isOcean(currentLocation.x-1, currentLocation.y)){
    		return new Point(currentLocation.x-1, currentLocation.y);
    	}
    	return currentLocation;
    }
    
    
    public Point goNorth(Point currentLocation){
    	if(currentLocation.y>0 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y-1)){
    		return new Point(currentLocation.x, currentLocation.y-1);
    	}
    	return currentLocation;
    	
    }
    
    public Point goSouth(Point currentLocation){
    	if(currentLocation.y<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y+1)){
    		return new Point(currentLocation.x, currentLocation.y+1);
    	}
    	return currentLocation;
    }
    
	
}
