import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;

public class TreasureQuest extends Application {
	
	Pane root, gameplayPane;	//game scene root pane
	Stage window;
	Scene menu, game;
	
	public DifficultySetting difficulty;	//dictates the monster factory.

	public GameFactory gameFactory;
	
	private boolean gameOver = false;
	
	public TreasureQuest(){
		difficulty = new EasyDifficulty();
	}
	
	
	@Override
	public void start(Stage mapStage) throws Exception {
		
		window = mapStage;
		
		//load in custom font
		Font.loadFont(getClass().getResourceAsStream("resources/fonts/ARCADEPI.TTF"), 12);
		
		Label difficultyLabel= new Label("Select Difficulty");
		Button buttonEasy = new Button("Easy");
		Button buttonMedium = new Button("Medium");
		Button buttonHard = new Button("Hard");
		
		ImageView mainIcon = new ImageView(new Image("Treasure-Quest.png"));
		mainIcon.setFitWidth(900);
		mainIcon.setPreserveRatio(true);
		
		//delegates the individual factory settings to each game play difficulty.
		buttonEasy.setOnAction(e -> {
			gameplayPane.getChildren().clear();
			difficulty = new EasyDifficulty();
			gameOver = false;
			//move this line into the factory
			OceanMap.getInstance().buildMap(difficulty);
			gameFactory = new GameFactory(difficulty, gameplayPane);
			window.setScene(game);
			startSailing();
			});
		
		buttonMedium.setOnAction(e -> {
			gameplayPane.getChildren().clear();
			difficulty = new MediumDifficulty();
			gameOver = false;
			OceanMap.getInstance().buildMap(difficulty);
			gameFactory = new GameFactory(difficulty, gameplayPane);
			window.setScene(game);
			startSailing();
			});
		
		buttonHard.setOnAction(e -> {
			gameplayPane.getChildren().clear();
			difficulty = new HardDifficulty();
			gameOver = false;
			OceanMap.getInstance().buildMap(difficulty);
			gameFactory = new GameFactory(difficulty, gameplayPane);
			window.setScene(game);
			startSailing();
			});
		
		//create home menu Vertical Layout
		VBox MenuLayout = new VBox(25);
		MenuLayout.getChildren().addAll(mainIcon, difficultyLabel, buttonEasy, buttonMedium, buttonHard);
		//create game screen vertical Layout
		root = new VBox();
		//create actual game screen thats square.
		gameplayPane = new AnchorPane();
		//color the background
		root.getStyleClass().add("game-background");
		
		//set the size of the actual game window to be a square (for grid purpose) 
		gameplayPane.setMaxWidth(800);
		gameplayPane.setMinHeight(800);
		
		root.getChildren().addAll(gameplayPane);
		
		
		menu = new Scene(MenuLayout, 1000, 800);
		game = new Scene(root, 1000, 800);
		
		
		game.getStylesheets().add("TreasureQuest.css");
		
		menu.getStylesheets().add("TreasureQuest.css");
		
		window.setScene(menu);
		window.setTitle("Treasure Quest");
		window.setResizable(false);
		window.show();
		
	}
	
	/*
	 * in key event handler, check each time for an inequality in the number of treasures
	 * between the treasure objects and the ocean map treasure locations.
	 * if a disagreement occurs in number of items, loop through the treasure icon objects and 
	 * create a tmp list that does not include the taken treasure
	 */

	
private void startSailing(){
		
		game.setOnKeyPressed(new EventHandler<KeyEvent>(){
		
			@Override
			public void handle(KeyEvent ke) {
				switch(ke.getCode()){
				case RIGHT:
					gameFactory.getShip().goEast();
					break;
				case LEFT:
					gameFactory.getShip().goWest();
					break;
				case UP:
					gameFactory.getShip().goNorth();
					break;
				case DOWN:
					gameFactory.getShip().goSouth();
					break;
				default:
					break;
				}

				//call gameFactory to update all the ship locations, check treasures left
				gameFactory.updateScreen(gameplayPane);
				if(OceanMap.getInstance().getGameWin() && !gameOver){
					gameOver = true;
					gameFactory.gameWonAnim(gameplayPane, menu, window);
					
				}
				
				if(OceanMap.getInstance().getGameLose() && !gameOver){
					gameOver = true;
					gameFactory.gameLostAnim(gameplayPane, menu, window);
				}
			}
		});
	}
	
	
	public static void main(String[] args) {
     	launch(args);
    }
}
