package minesweeper;

import javafx.scene.control.Button;
import javafx.scene.shape.Rectangle;

public class DecoratedSquare extends Button{
    private Square square;
	
	public DecoratedSquare(Square square) {
		this.square = square;
		decorateSquare();
	}
	
	private void decorateSquare() {
		setMaxWidth(25);
		setMaxHeight(25);
		setMinWidth(25);
		setMinHeight(25);
		setShape(new Rectangle(25, 25));
		setStyle("-fx-border-color: black; -fx-background-color: lightgray; -fx-border-width: 1px;");
	}
	
	public Square getSquare() {
		return square;
	}
}
