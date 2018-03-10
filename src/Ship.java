import java.awt.Point;
import java.util.Observable;

public class Ship extends Observable {
    Point currentLocation;
    
    public Ship(){    	
    	currentLocation = OceanMap.getInstance().getShipLocation();
    }
    
    public Point getLocation(){
    	return currentLocation;
    }
    
    public void goEast(){
    	if(currentLocation.x<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x+1, currentLocation.y)){
    		currentLocation.x++;
    		OceanMap.getInstance().checkForTreasure((int)currentLocation.getX(), (int)currentLocation.getY());
    		setChanged();
    		notifyObservers();
    	}  			
    }
    
    public void goWest(){
    	if(currentLocation.x >0 && OceanMap.getInstance().isOcean(currentLocation.x-1, currentLocation.y)){
    		currentLocation.x--;
    		OceanMap.getInstance().checkForTreasure((int)currentLocation.getX(), (int)currentLocation.getY());
    		setChanged();
    		notifyObservers();
    	}  			
    }
    
    public void goNorth(){
    	if(currentLocation.y>0 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y-1)){
    		currentLocation.y--;
    		OceanMap.getInstance().checkForTreasure((int)currentLocation.getX(), (int)currentLocation.getY());
    		setChanged();
    		notifyObservers();
    	}  			
    }
    
    public void goSouth(){
    	if(currentLocation.y<OceanMap.getInstance().getDimensions()-1 && OceanMap.getInstance().isOcean(currentLocation.x, currentLocation.y+1)){
    		currentLocation.y++;
    		OceanMap.getInstance().checkForTreasure((int)currentLocation.getX(), (int)currentLocation.getY());
    		setChanged();
    		notifyObservers();
    	}  			
    }
	
}