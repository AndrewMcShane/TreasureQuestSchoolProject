import java.awt.Point;

public class VerticalSearch implements SearchStrategy {
	
	private String name;
	private int direction = 1;	//switches between 1/-1.
	public VerticalSearch(){
		this.name = "Vertical Search";
	}
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		
		if(direction == 1){
			return goNorth(currentLocation);
		} else if (direction == -1){
			return goSouth(currentLocation);
		}
		return null;
	}
	
    public Point goNorth(Point currentLocation){
    	if(currentLocation.y>0 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y-1)){
    		return new Point(currentLocation.x, currentLocation.y-1);
    	}else{
    		direction = -1;
    	}
    	return currentLocation;
    	
    }
    
    public Point goSouth(Point currentLocation){
    	if(currentLocation.y<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y+1)){
    		return new Point(currentLocation.x, currentLocation.y+1);
    	}  else direction = 1;
    	
    	return currentLocation;
    }

}
