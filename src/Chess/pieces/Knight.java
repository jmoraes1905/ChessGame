package Chess.pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p==null || p.getColor()!=this.getColor();
	}
	
	@Override
	public boolean[][] possibleMoves() {
		//Check positions similarly to the King
		boolean mat[][] = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
		Position p = new Position(0,0);
		
		
		p.setValues(position.getRow()-1,position.getColumn()-2);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
	
		p.setValues(position.getRow()-2,position.getColumn()-1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()-2,position.getColumn()+1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()-1,position.getColumn()+2);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()+1,position.getColumn()+2);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()+2,position.getColumn()+1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()+2,position.getColumn()-1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		p.setValues(position.getRow()+1,position.getColumn()-2);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}

	@Override
	public String toString() {
		return "N";
	}
}