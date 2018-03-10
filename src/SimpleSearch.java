import java.awt.Point;

public class SimpleSearch implements SearchStrategy {

	private String name;
	
	public SimpleSearch(){
		this.name = "Simple Search";
	}
	
	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public Point move(Point shipLocation, Point currentLocation) {
		int distX = currentLocation.x - shipLocation.x; //  x > 0 = to the left, x < 0 = to the right.
		int distY = currentLocation.y - shipLocation.y; // y > 0 = above, y < 0 ship is below.
		//true if the attempted move location is an island.
		//keep a distance of 1 

		//if x is further
		if(Math.abs(distX) >= Math.abs(distY)){
			if(distX < 0){
				 if(OceanMap.getInstance().isOcean(currentLocation.x+1, currentLocation.y)){
					return new Point(currentLocation.x+1, currentLocation.y);
				 }
			} else{
				 if(OceanMap.getInstance().isOcean(currentLocation.x-1, currentLocation.y)){
					 return new Point(currentLocation.x-1, currentLocation.y);
				 }
			}
		} 
		//otherwise if y is further
		else{
			if(distY < 0){
				 if(OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y+1)){
					 return new Point(currentLocation.x, currentLocation.y+1);
				 }
			} else{
				 if(OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y-1)){
					 return new Point(currentLocation.x, currentLocation.y-1);
				 }
			}
		}
		
		return currentLocation;
	}
	}
	
	
	
