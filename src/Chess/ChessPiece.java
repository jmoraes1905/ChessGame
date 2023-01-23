package Chess;

import Boardgame.Board;
import Boardgame.Piece;
import Boardgame.Position;

public abstract class ChessPiece extends Piece {
	
	private Color color;
	private int moveCount;

	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
	
	public int getMoveCount() {
		return moveCount;
	}
	public void increaseMoveCount() {
		moveCount++;
	}
	
public void decreaseMoveCount() {
		moveCount--;
	}
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p =(ChessPiece)this.getBoard().piece(position);
		return p!= null && p.getColor()!=this.color;
	}
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	

}
