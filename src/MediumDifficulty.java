
public class MediumDifficulty implements DifficultySetting {

	private String name;
	private int numMonsters;
	private int numTreasures;
	private int mapSize;
	private int numIslands;
	
	public MediumDifficulty(){
		this.name = "Medium";
		this.numMonsters = 3;
		this.numTreasures = 2;
		this.mapSize = 20;
		this.numIslands = 30;	
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
