package Boardgame;

public class Piece {
	//We don't want this matrix position to be seen by the chess layer
	protected Position position;
	private Board board;
	
	public Piece(Board board) {
		this.board = board;
		this.position = null;
	}
	//We dont want to expose the board to the chess layer, just to Pieces subclasses
	protected Board getBoard() {
		return board;
	}
	
	
}
