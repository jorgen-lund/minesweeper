package minesweeper;

import java.util.ArrayList;
import java.util.List;

public class Square {

    private boolean mine;
	private boolean flagged;
	private boolean flipped;
	private List<Square> neighbors;
	private Minefield minefield;
	

	public Square(boolean mine, Minefield minefield) {
		this.minefield = minefield;
		neighbors = new ArrayList<>();
		this.flagged = false;
		this.flipped = false;
		this.mine = mine;
	}
	
	public int countMines() {
		int counter = 0;
		for (Square square : neighbors) {
			if (square.isMine()) {
				counter ++;
			}
		}
		return counter;
	}
	
	public void openTile() {
		setFlipped(true);
		minefield.incrementFlipped();
		int n = countMines();
		if (n == 0) {
			for (Square square : getNeighbors()) {
				if (!square.isFlipped()) {
					square.openTile();
				}
			}
		}
	}
	
	public void addNeighbors(Square neighbor) {
		neighbors.add(neighbor);
	}
	
	public List<Square> getNeighbors() {
		return neighbors;
	}
	
	public int getNumberOfNeighbors() {
		return neighbors.size();
	}
	
	
	public void setMine(boolean setter) {
		this.mine = setter;
	}
	
	public boolean isMine() {
		return mine;
	}
	

	public void setFlipped(boolean setter) {
		this.flipped = setter;
		if (flipped) {
			minefield.incrementFlipped();
		}
	}	

    public boolean isFlipped() {
		return flipped;
	}		


	public void setFlagged(boolean setter) {
		this.flagged = setter;
	}	

	public boolean isFlagged() {
		return flagged;
	}
}
