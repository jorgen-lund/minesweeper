package minesweeper;

import java.util.Random;

public class Minefield {
    
    private int height;
	private int width;
	private Square[][] board;
	private final double MINECHANCE = 0.125;
	private int flippedSquares = 0;
	private boolean isWon = false;
	
	
	public Minefield(int height, int width) {
		setHeight(height);
		setWidth(width);
		this.board = createMinefield();
		setNeighbors();
	}
	
	private Square[][] createMinefield() {
		Square[][] board = new Square[height][width];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				double d = new Random().nextDouble();
				boolean mine = (d < MINECHANCE); 
				board[y][x] = new Square(mine, this);
			}
		}
		return board;
	}
	
	
	public int getTotalMineCount() {
		int count = 0;
		for (Square[] row : board) {
			for (Square square : row) {
				if (square.isMine()) {
					count++;
				}
			}
		}
		return count;
	}
	
	public int getSafeSquareCount() {
		int totalSquares = height * width;
		return totalSquares - getTotalMineCount();
	}
	
	private void setNeighbors() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				Square current = board[y][x];
				if (y != 0) {
					current.addNeighbors(board[y-1][x]);
					//legger til den som er over. 
				}
				if (x != 0) {
					current.addNeighbors(board[y][x-1]);
					//legger til den som er til venstre
				}
				if (y != height - 1) {
					current.addNeighbors(board[y + 1][x]);
					//legger til den som er under
				}
				if (x != width - 1) {
					current.addNeighbors(board[y][x+1]);
					//legger til den som er til h yre
				}
				if (y != 0 && x != width - 1) {
					current.addNeighbors(board[y-1][x+1]);
					//legger til den som er N 
				}
				if (y != height - 1 && x != 0) {
					current.addNeighbors(board[y+1][x-1]);
					//legger til den som er SV
				}
				if (y != height - 1 && x != width -1) {
					current.addNeighbors(board[y+1][x+1]);
					//legger til den som er S 
				}
				if (y != 0 && x != 0) {
					current.addNeighbors(board[y-1][x-1]);
					//legger til den som er NV
				}
			}
		}
	}
	public void setHeight(int height) {
		if (height > 0 && height < 12) {
			this.height = height;
		}
		else {
			throw new IllegalArgumentException("The height of the board must consist of a positive integer from 1 up to 11");
		}
	}
	
	public void setWidth(int width) {
		if (width > 0 && width < 23) {
			this.width = width;
		}
		else {
			throw new IllegalArgumentException("The width of the board must consist of positive integer from 1 up to 22");
		}
	}
	
	public int getHeight() {
		return height;
	}
	
	public int getWidth() {
		return width;
	}
	
	public Square[][] getBoard() {
		return board;
	}
	
	public void incrementFlipped() {
		flippedSquares ++;
		if (flippedSquares == getSafeSquareCount()) {
			isWon = true;
		}
	}
	
	public boolean isWon() {
		return isWon;
	}	
}
