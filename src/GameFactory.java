import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;


public class GameFactory {

	public Ship ship;
	public Map<Treasure, ImageView> treasures = new HashMap<Treasure, ImageView>();
	
	public Map<Monster, ImageView> monsters = new HashMap<Monster, ImageView>();
	//one more linked list for monster images.
	
	DifficultySetting difficulty;
	
	//images for 
	Image shipImage;
	Image pirateImage;
	Image giantSquidImage;
	Image treasureImage;
	
	//ship image-view 
	ImageView shipImgV;
	
	
	//changes based on grid size
	double scaleFactor;
	
	
	
	public GameFactory(DifficultySetting difficulty, Pane layoutPane){
		/*
		 * create monsters and monster images,
		 * instance the classes appropriately,
		 * add observers,
		 * persist the monsters and images in self contained list
		 * update function
		 */
		//set the difficulty.
		this.difficulty = difficulty;
		
		//set default scale factor then calculate the exact scale factor fit to the window size.
		scaleFactor = 50;
		calcScaleFactor(difficulty.getMapSize(), layoutPane);
		//instance the new images.
		shipImage = new Image("PlayerIcon.png", scaleFactor,scaleFactor,true,true);
		pirateImage = new Image("PirateIcon.png", scaleFactor, scaleFactor, true, true);
		giantSquidImage = new Image("GiantSquidIcon.png", scaleFactor, scaleFactor, true, true);
		treasureImage = new Image("TreasureIcon.png", scaleFactor, scaleFactor, true, true);
		

		
		drawMap(layoutPane);
		
		if(difficulty.getDifficulty() == "Easy"){
			createEasyDifficulty(layoutPane);
		} else if (difficulty.getDifficulty() == "Medium"){
			createMediumDifficulty(layoutPane);
		} else if (difficulty.getDifficulty() == "Hard"){
			createHardDifficulty(layoutPane);
		}
		
		
	}
	
	
	public void calcScaleFactor(int gridSize, Pane pane){
		double screenHeight = pane.getScene().getHeight();
		scaleFactor = (screenHeight/gridSize);	//800 is the size of the game pane usually.
	}
	
	
	public Ship getShip(){
		return this.ship;
	}
	

	public void drawMap(Pane gameRoot){
		Image islandImage = new Image("IslandIcon.png",scaleFactor, scaleFactor, true, true);
		for(int x = 0; x < difficulty.getMapSize(); x++){
			for(int y = 0; y < difficulty.getMapSize(); y++){
				Rectangle rect = new Rectangle(x*scaleFactor,y*scaleFactor,scaleFactor,scaleFactor);
				rect.setStroke(Color.CADETBLUE);
				if(!OceanMap.getInstance().isOcean(x, y))
				    rect.setFill(new ImagePattern(islandImage));
					
				else
					rect.setFill(Color.PALETURQUOISE);
				gameRoot.getChildren().add(rect);
			}
		}
	}
	
	
	//updates all positions and triggers the win or lose screens. 
	public void updateScreen(Pane gameRoot){
		/*
		 * iterate over ship, treasures, monsters. if treasure count changes, 
		 * run check of treasures and check game over. 
		 * update monsters positions
		 * check game lost.
		 */
		
		//first ship
		shipImgV.setX(ship.getLocation().x*scaleFactor);
		shipImgV.setY(ship.getLocation().y*scaleFactor);
			
		//then treasures
			//check if a treasure has been picked up. (I will assume that the treasure was picked up in this frame.)

		Iterator<Entry<Treasure, ImageView>> treasureEntries = treasures.entrySet().iterator();
		while(treasureEntries.hasNext()){
			Entry<Treasure, ImageView> entry = treasureEntries.next();
			if(ship.getLocation().x == entry.getKey().getLocation().x 
					&& ship.getLocation().y == entry.getKey().getLocation().y){
				//removes the entry from the screen, then remove it from the list.

				gameRoot.getChildren().remove(entry.getValue());
				treasures.remove(entry.getKey());
				break;
			}
		}
		
		//now monsters
		Iterator<Entry<Monster,ImageView>> monsterEntries = monsters.entrySet().iterator();
		while(monsterEntries.hasNext()){
			Entry<Monster,ImageView> entry = monsterEntries.next();
			entry.getValue().setX(entry.getKey().getLocation().x*scaleFactor);
			entry.getValue().setY(entry.getKey().getLocation().y*scaleFactor);	
		}		
	}
	
	
	public void gameWonAnim(Pane gameRoot, Scene goBackToScene, Stage window ){
		/*
		 * remove all, add back the ship.
		 * display message "You won!"
		 * change message to "returning to main menu"
		 */
		gameRoot.getChildren().clear();
		gameRoot.getChildren().add(shipImgV);
		
		Label winnerLabel = new Label("You Win!");
		

		
		
		VBox overlayPane = new VBox(20);
		overlayPane.getChildren().add(winnerLabel);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(overlayPane);
		gameRoot.getChildren().add(stackPane);
		
		overlayPane.setMinHeight(overlayPane.getScene().getHeight());
		overlayPane.setMinWidth(overlayPane.getScene().getWidth());
		overlayPane.setStyle("-fx-alignment:center;");
		
		//actual animation timeline by each key event frame.
		Timeline timeline = new Timeline();
		
		KeyFrame key1 = new KeyFrame(
				Duration.seconds(2),
				event -> {
					winnerLabel.setText("Returning to the main menu");
				});
		KeyFrame key2 = new KeyFrame(
				Duration.seconds(4),
				event -> {
					window.setScene(goBackToScene);
				});
		timeline.getKeyFrames().addAll(key1, key2);
		
		timeline.play();
		
		
		return;
	}
	
	public void gameLostAnim(Pane gameRoot, Scene goBackToScene, Stage window){
		
		
		gameRoot.getChildren().clear();
		gameRoot.getChildren().add(shipImgV);
		
		Label loseLabel = new Label("You Were Sunk by monsters");
		
		VBox overlayPane = new VBox(20);
		overlayPane.getChildren().add(loseLabel);
		StackPane stackPane = new StackPane();
		stackPane.getChildren().add(overlayPane);
		gameRoot.getChildren().add(stackPane);
		
		overlayPane.setMinHeight(overlayPane.getScene().getHeight());
		overlayPane.setMinWidth(overlayPane.getScene().getWidth());
		overlayPane.setStyle("-fx-alignment:center;");
		
		//actual animation timeline by each key event frame.
		Timeline timeline = new Timeline();
		
		KeyFrame key1 = new KeyFrame(
				Duration.seconds(2),
				event -> {
					loseLabel.setText("Returning to the main menu");
				});
		KeyFrame key2 = new KeyFrame(
				Duration.seconds(4),
				event -> {
					window.setScene(goBackToScene);
				});
		timeline.getKeyFrames().addAll(key1, key2);
		
		timeline.play();
		
		
		return;
	}
	
	private void createEasyDifficulty(Pane gameRoot){
		//place ship, place treasures, place monsters... in that order.
		
		//place ship.
		ship = new Ship();
		
		//update image-view of ship
		shipImgV = new ImageView(shipImage);
		shipImgV.setX(ship.getLocation().x*scaleFactor);
		shipImgV.setY(ship.getLocation().y*scaleFactor);
		
		gameRoot.getChildren().add(shipImgV);
		
		//place treasures.
		for(int i = 0; i < difficulty.getNumTreasures(); i++){
			Treasure tmpTreasure = new Treasure();
			ImageView tmpTreasureImgV = new ImageView(treasureImage);
			//add the new instances to the mapping.
			treasures.put(tmpTreasure, tmpTreasureImgV);
			//place and add them to the root pane.
			tmpTreasureImgV.setX(tmpTreasure.getLocation().x*scaleFactor);
			tmpTreasureImgV.setY(tmpTreasure.getLocation().y*scaleFactor);
			gameRoot.getChildren().add(tmpTreasureImgV);
			
		}
		
		//place Monsters, set search strategy, place images, add new instances to mapping, place and add to root pane.
		PirateMonster pirate1 = new PirateMonster();
		
		pirate1.setSearchStrategy(new RandomSearch());
		
		GiantSquidMonster kraken1 = new GiantSquidMonster();
		kraken1.setSearchStrategy(new HorizontalSearch());
		
		
		ImageView tmpPirate1ImgV = new ImageView(pirateImage);
		ImageView tmpKraken1ImgV = new ImageView(giantSquidImage);
		
		monsters.put(pirate1, tmpPirate1ImgV);
		monsters.put(kraken1, tmpKraken1ImgV);
		
		tmpPirate1ImgV.setX(pirate1.getLocation().x*scaleFactor);
		tmpPirate1ImgV.setY(pirate1.getLocation().y*scaleFactor);
		
		tmpKraken1ImgV.setX(kraken1.getLocation().x*scaleFactor);
		tmpKraken1ImgV.setY(kraken1.getLocation().y*scaleFactor);
		
		ship.addObserver(kraken1);
		ship.addObserver(pirate1);
		
		gameRoot.getChildren().addAll(tmpPirate1ImgV, tmpKraken1ImgV);
		
	}
	
	
	private void createMediumDifficulty(Pane gameRoot){
		ship = new Ship();
		
		shipImgV = new ImageView(shipImage);
		shipImgV.setX(ship.getLocation().x*scaleFactor);
		shipImgV.setY(ship.getLocation().y*scaleFactor);
		
		gameRoot.getChildren().add(shipImgV);
		
		for(int i = 0; i < difficulty.getNumTreasures(); i++){
			Treasure tmpTreasure = new Treasure();
			ImageView tmpTreasureImgV = new ImageView(treasureImage);
			//add the new instances to the mapping.
			treasures.put(tmpTreasure, tmpTreasureImgV);
			//place and add them to the root pane.
			tmpTreasureImgV.setX(tmpTreasure.getLocation().x*scaleFactor);
			tmpTreasureImgV.setY(tmpTreasure.getLocation().y*scaleFactor);
			gameRoot.getChildren().add(tmpTreasureImgV);
			
		}
		
		PirateMonster pirate1 = new PirateMonster();
		PirateMonster pirate2 = new PirateMonster();
		GiantSquidMonster kraken1 = new GiantSquidMonster();
		
		pirate1.setSearchStrategy(new SimpleSearch());
		pirate2.setSearchStrategy(new RandomSearch());
		kraken1.setSearchStrategy(new VerticalSearch());
		
		ImageView tmpPirate1ImgV = new ImageView(pirateImage);
		ImageView tmpPirate2ImgV = new ImageView(pirateImage);
		ImageView tmpKraken1ImgV = new ImageView(giantSquidImage);
		
		monsters.put(pirate1, tmpPirate1ImgV);
		monsters.put(pirate2, tmpPirate2ImgV);
		monsters.put(kraken1, tmpKraken1ImgV);
		
		tmpPirate1ImgV.setX(pirate1.getLocation().x*scaleFactor);
		tmpPirate1ImgV.setY(pirate1.getLocation().y*scaleFactor);
		
		tmpPirate1ImgV.setX(pirate2.getLocation().x*scaleFactor);
		tmpPirate1ImgV.setY(pirate2.getLocation().y*scaleFactor);
		
		tmpKraken1ImgV.setX(kraken1.getLocation().x*scaleFactor);
		tmpKraken1ImgV.setY(kraken1.getLocation().y*scaleFactor);
		
		ship.addObserver(pirate1);
		ship.addObserver(pirate2);
		ship.addObserver(kraken1);
		
		gameRoot.getChildren().addAll(tmpPirate1ImgV, tmpPirate2ImgV, tmpKraken1ImgV);
		
	}
	
	private void createHardDifficulty(Pane gameRoot){
		ship = new Ship();
		
		shipImgV = new ImageView(shipImage);
		shipImgV.setX(ship.getLocation().x*scaleFactor);
		shipImgV.setY(ship.getLocation().y*scaleFactor);
		
		gameRoot.getChildren().add(shipImgV);
		
		for(int i = 0; i < difficulty.getNumTreasures(); i++){
			Treasure tmpTreasure = new Treasure();
			ImageView tmpTreasureImgV = new ImageView(treasureImage);
			//add the new instances to the mapping.
			treasures.put(tmpTreasure, tmpTreasureImgV);
			//place and add them to the root pane.
			tmpTreasureImgV.setX(tmpTreasure.getLocation().x*scaleFactor);
			tmpTreasureImgV.setY(tmpTreasure.getLocation().y*scaleFactor);
			gameRoot.getChildren().add(tmpTreasureImgV);
			
		}
		
		PirateMonster pirate1 = new PirateMonster();
		PirateMonster pirate2 = new PirateMonster();
		GiantSquidMonster kraken1 = new GiantSquidMonster();
		GiantSquidMonster kraken2 = new GiantSquidMonster();

		pirate1.setSearchStrategy(new SimpleSearch());
		pirate2.setSearchStrategy(new SimpleSearch());
		kraken1.setSearchStrategy(new RandomSearch());
		kraken2.setSearchStrategy(new RandomSearch());
		
		ImageView tmpPirate1ImgV = new ImageView(pirateImage);
		ImageView tmpPirate2ImgV = new ImageView(pirateImage);
		ImageView tmpKraken1ImgV = new ImageView(giantSquidImage);
		ImageView tmpKraken2ImgV = new ImageView(giantSquidImage);
		
		monsters.put(pirate1, tmpPirate1ImgV);
		monsters.put(pirate2, tmpPirate2ImgV);
		monsters.put(kraken1, tmpKraken1ImgV);
		monsters.put(kraken2, tmpKraken2ImgV);

		tmpPirate1ImgV.setX(pirate1.getLocation().x*scaleFactor);
		tmpPirate1ImgV.setY(pirate1.getLocation().y*scaleFactor);
		
		tmpPirate1ImgV.setX(pirate2.getLocation().x*scaleFactor);
		tmpPirate1ImgV.setY(pirate2.getLocation().y*scaleFactor);
		
		tmpKraken1ImgV.setX(kraken1.getLocation().x*scaleFactor);
		tmpKraken1ImgV.setY(kraken1.getLocation().y*scaleFactor);
		
		tmpKraken2ImgV.setX(kraken2.getLocation().x*scaleFactor);
		tmpKraken2ImgV.setY(kraken2.getLocation().y*scaleFactor);
		
		ship.addObserver(pirate1);
		ship.addObserver(pirate2);
		ship.addObserver(kraken1);
		ship.addObserver(kraken2);
		
		gameRoot.getChildren().addAll(tmpPirate1ImgV, tmpPirate2ImgV, tmpKraken1ImgV, tmpKraken2ImgV);
	}
	
}
