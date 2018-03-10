
public class EasyDifficulty implements DifficultySetting {

	private String name;
	private int numMonsters;
	private int numTreasures;
	private int mapSize;
	private int numIslands;
	
	public EasyDifficulty(){
		this.name = "Easy";
		this.numMonsters = 2;
		this.numTreasures = 1;
		this.mapSize = 10;
		this.numIslands = 10;	
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
