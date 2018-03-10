import java.awt.Point;

public class HorizontalSearch implements SearchStrategy {

	private String name;
	private int direction = 1;	//swaps between 1/-1.
	
	public HorizontalSearch(){
		this.name = "Horizontal Search";
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		
		if(direction == 1){
			return goEast(currentLocation);
		} else if (direction == -1){
			return goWest(currentLocation);
		}
		
		return null;
	}
	
	
    public Point goEast(Point currentLocation){
    	if(currentLocation.x<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x+1, currentLocation.y)){
    		return new Point(currentLocation.x+1, currentLocation.y);
    	} else{
    		direction = -1;
    	}
    	return currentLocation;
    }
    
    public Point goWest(Point currentLocation){
    	if(currentLocation.x >0 && OceanMap.getInstance().isOcean(currentLocation.x-1, currentLocation.y)){
    		return new Point(currentLocation.x-1, currentLocation.y);
    	} else{
    		direction = 1;
    	}
    	return currentLocation;
    }

}
