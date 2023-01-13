package Boardgame;

public class Piece {
	//We don't want this matrix position to be seen by the chess layer
	protected Position position;
	private Board board;
	public Piece(Board board) {
		this.board = board;
	}
	
	
}
