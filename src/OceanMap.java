import java.awt.Point;
import java.util.Random;

// This class is responsible for generate a grid representing the island map
// and randomly placing islands onto the grid.
public class OceanMap {
	
	private static OceanMap uniqueInstance;
	
	
	boolean[][] islands;
	int dimensions;
	int islandCount;
	Random rand = new Random();
	Point shipLocation;
	int treasuresLeft;
	Point[] treasureLocations;
	
	//checked each time 
	private boolean gameWin = false;
	boolean gameLose = false;
	
	
	// Constructor
	// Not adding validation code so make sure islandCount is much less than dimension^2
	private OceanMap(){
		//default setting when constructed first time.
		buildMap(new EasyDifficulty());
	}
	
	public static OceanMap getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new OceanMap();
		}
		return uniqueInstance;
	}
	
	//responsible for building the map when a difficulty setting is selected and the game starts. 
	public void buildMap(DifficultySetting difficulty){
		this.dimensions = difficulty.getMapSize();
		this.islandCount = difficulty.getNumIslands();
		createGrid();
		placeIslands();
		shipLocation = placeShip();
		//nullify treasure locations for multiple games.
		treasureLocations = null;
		treasuresLeft = 0;
		gameWin = false;
		gameLose = false;
		
		
	}
	
	
	// Create an empty map
	private void createGrid(){
		 islands = new boolean[dimensions][dimensions];
		 for(int x = 0; x < dimensions; x++)
			 for(int y = 0; y < dimensions; y++)
				 islands[x][y] = false;
	}
	
	// Place islands onto map
	private void placeIslands(){
		int islandsToPlace = islandCount;
		while(islandsToPlace >0){
			int x = rand.nextInt(dimensions);
			int y = rand.nextInt(dimensions);
			if(islands[x][y] == false){
				islands[x][y] = true;
				islandsToPlace--;
			}
		}
	}
	
	private Point placeShip(){
		boolean placedShip = false;
		int x=0,y=0;
		while(!placedShip){
			x = rand.nextInt(dimensions);
			y = rand.nextInt(dimensions);
			if(islands[x][y] == false){
				placedShip = true;
			}
		}
		return new Point(x,y);
	}
	
	//Bug: treasure being placed on 
	public Point placeTreasure(){
		boolean placedTreasure = false;
		int x=0,y=0;
		//Point ship = getShipLocation();
		while(!placedTreasure){
			x = rand.nextInt(dimensions);
			y = rand.nextInt(dimensions);
			
			//if location is ocean and not a treasure.
			if(isOcean(x,y) && !isTreasure(x,y)){
				
				placedTreasure = true;
				
				}	
			}
		//put the treasure in the locations list and increase number of treasures left.
		Point location = new Point(x,y);
		treasuresLeft++;
		
		if(treasureLocations == null){
			treasureLocations = new Point[1];
			treasureLocations[0] = location;
		}else{
			Point[] tmp = new Point[treasureLocations.length+1];
			for(int i = 0; i < treasureLocations.length; i++){
				tmp[i] = treasureLocations[i];
			}
			tmp[treasureLocations.length] = location;
			treasureLocations = tmp;
		}

		return location;
	}
	
	public Point placeMonster(){
		boolean placedMonster = false;
		int x=0,y=0;
		Point ship = getShipLocation();
		while(!placedMonster){
			x = rand.nextInt(dimensions);
			y = rand.nextInt(dimensions);
			if(islands[x][y] == false && !(x!= ship.getX() && y!= ship.getY())){	//prevents pirate being placed on ship
				//prevents monster being placed on top of treasure.
				for(int i = 0; i < treasureLocations.length; i++){
					if(treasureLocations[i].getX() == x && treasureLocations[i].getY() == y){
						placedMonster = false;
						break;
					} else placedMonster = true;
				}
			}
		}
		return new Point(x,y);
	}

	public Point getShipLocation(){
		return shipLocation;
	}
	
	
	// Return generated map
	public boolean[][] getMap(){
		return islands;
	}
	
	public int getDimensions(){
		return dimensions;
	}
	
	public boolean isOcean(int x, int y){
		if (!islands[x][y])
			return true;
		else
			return false;
	}
	
	
	public boolean isTreasure(int x, int y){
		if(treasureLocations == null) return false;
		for(int i = 0; i < treasureLocations.length; i++){
			if(treasureLocations[i].getX() == x && treasureLocations[i].getY() == y){
				return true;
			}
		}
		return false;
	}
	
	//is the current location containing a treasure.
	public boolean checkForTreasure(int x, int y){
		for(int i = 0; i < treasureLocations.length; i++){
			if(treasureLocations[i].getX() == x && treasureLocations[i].getY() == y){
				this.treasuresLeft--;
				removeTreasure(x,y);
				checkGameWin(allTreasuresGotten());
				return true;
			}
		}
		return false;
	}
	
	//decreases treasure locations on the map.
	private void removeTreasure(int x, int y){
		Point[] tmp = new Point[treasureLocations.length - 1];
		int tmpIndex = 0;
		for(int i = 0; i < treasureLocations.length; i++){
			if(treasureLocations[i].getX() != x || treasureLocations[i].getY() != y){
				tmp[tmpIndex] = treasureLocations[i];
				tmpIndex++;
			}
		}
		treasureLocations = tmp;
		return;
		
	}
	
	private boolean allTreasuresGotten(){
		if(this.treasuresLeft > 0) return false;
		else return true;
	}
	
	private void checkGameWin(boolean state){
		if(!state) return;
		else this.gameWin = true;
		return;
	}
	
	
	//monsters got to the ship
	public void gameLose(){
		this.gameLose = true;
	}
	
	public boolean getGameWin(){
		return this.gameWin;
	}
	
	public boolean getGameLose(){
		return this.gameLose;
	}
	
	
	
}