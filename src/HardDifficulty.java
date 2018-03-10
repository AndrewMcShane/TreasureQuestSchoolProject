
public class HardDifficulty implements DifficultySetting {

	private String name;
	private int numMonsters;
	private int numTreasures;
	private int mapSize;
	private int numIslands;
	
	public HardDifficulty(){
		this.name = "Hard";
		this.numMonsters = 5;
		this.numTreasures = 3;
		this.mapSize = 30;
		this.numIslands = 65;	
	}
	
	@Override
	public String getDifficulty() {
		return this.name;
	}

	@Override
	public int getNumMonsters() {
		return this.numMonsters;
	}

	@Override
	public int getNumTreasures() {
		return this.numTreasures;
	}

	@Override
	public int getMapSize() {
		return this.mapSize;
	}

	@Override
	public int getNumIslands() {
		return this.numIslands;
	}

}
