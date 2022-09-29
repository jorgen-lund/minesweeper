package minesweeper;

import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.input.MouseButton;

public class AppController {
    private Minefield minefield;
	private long startTime;
	private double gameTime;
	private int height = 11;
	private int width = 20;
	private ScoresSaver scoresSaver = new ScoresSaver("scores.txt");
	
	GridPane gamePane = new GridPane();
	@FXML TextField timeUsed, bestTime;
	@FXML Pane board;
	@FXML Button btnStartGame, saveScore;
	
	
	
	public void startGame() throws FileNotFoundException {
		
		timeUsed.setText(null);
		board.getChildren().clear();
		gamePane.setDisable(false);
		board.toFront();
		loadHighScore();
		minefield = new Minefield(height, width);
		for (int y = 0; y < minefield.getHeight(); y++) {
			for (int x = 0; x < minefield.getWidth(); x++) {
				Square square = minefield.getBoard()[y][x];
				DecoratedSquare decSquare = new DecoratedSquare(square);
				setMouseClick(decSquare);
				gamePane.add(decSquare, x, y);
			}
		}
		startTimer();		
		board.getChildren().add(gamePane);
		btnStartGame.setText("New game");
	}
	
	private void updateBoard() {
		
		for (Node node : gamePane.getChildren()) {
			DecoratedSquare decSquare = (DecoratedSquare)node;
			Square square = decSquare.getSquare();
			//metoden getChildren returnerer en liste av Noder, som er en superklasse
			//Vi prøver derfor å caste den Noden til en Square. 
			if (square.isFlipped()) {
				decSquare.setStyle("-fx-border-color: black; -fx-background-color: white; -fx-border-width: 1px;");
				int mines= square.countMines();
				if (mines != 0) {
					decSquare.setText("" + mines);
				}
			}
		
		}
	}
	
	private void setMouseClick(DecoratedSquare buttonSquare) {
		buttonSquare.setOnMouseClicked( ev -> { 
          if(ev.getButton() == MouseButton.SECONDARY) {
              handleRightClick(buttonSquare);
          } else {
              handleLeftClick(buttonSquare);
          }

		});
	}	
		
	
	private void handleRightClick(DecoratedSquare buttonSquare) {
		try {
			Square square = buttonSquare.getSquare();
			if (square.isFlipped()) {
				throw new IllegalArgumentException("Can't flag an opened tile");
			}
			if (square.isFlagged()) {
				square.setFlagged(false);
				buttonSquare.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-border-width: 1px;");
			}
			else if (!square.isFlagged()) {
				square.setFlagged(true);
				buttonSquare.setStyle("-fx-border-color: black; -fx-background-color: red; -fx-border-width: 1px;");				
			}
			else {
				buttonSquare.setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-border-width: 1px;");
			}
		}
		catch(IllegalArgumentException e) {
			alertBox(e.getMessage());
		}
	}
	
	private void handleLeftClick(DecoratedSquare buttonSquare) {
		try {
			Square s = buttonSquare.getSquare();
			if(s.isFlagged()) {
				throw new IllegalArgumentException("Can't open a flagged tile");
			}
			else if (s.isMine()) {
				buttonSquare.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-border-width: 1px;");
				gamePane.setDisable(true);
				throw new IllegalArgumentException("GAME OVER!");
			}
			else {
				s.openTile();
				updateBoard();
				if (minefield.isWon()) {
					stopTimer();
					registerWonGame();
				}
			}
		}
		catch(IllegalArgumentException e) {
			alertBox(e.getMessage());
		}
	}
	
	private Alert alertBox(String errormessage) {
		Alert box = new Alert(AlertType.WARNING);
		
		box.setContentText(errormessage);
		box.show();
		return box;
	}
	
	private void registerWonGame() {
		stopTimer();
		alertBox("You win! All mines found. Save this score and see if you can beat it ;)");
	}

	private void startTimer() {
		startTime = System.currentTimeMillis();
	}
	
	private void stopTimer() {
		long stop = System.currentTimeMillis();
		gameTime = (stop - startTime) / 1000;
		String gameTimeInString = String.valueOf(gameTime);
		timeUsed.setText(gameTimeInString);
	}
	
	private void loadHighScore() {
		//laster inn og setter best score sin test.
		try {
			double infoFromFile = scoresSaver.readFromFile();
			String infoAsString = String.valueOf(infoFromFile);
			bestTime.setText(infoAsString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void saveScore() {
		try {
			scoresSaver.writeToFile(gameTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
