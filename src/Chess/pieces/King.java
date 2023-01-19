package Chess.pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "K";
	}

	private boolean canMove(Position position) {
		ChessPiece p = (ChessPiece)getBoard().piece(position);
		return p==null || p.getColor()!=this.getColor();
	}
	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
		Position p = new Position(0,0);
		
		//Check above position
		p.setValues(position.getRow()-1,position.getColumn());
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//bellow
		p.setValues(position.getRow()+1,position.getColumn());
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//right
		p.setValues(position.getRow(),position.getColumn()+1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//left
		p.setValues(position.getRow(),position.getColumn()-1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//north-east
		p.setValues(position.getRow()-1,position.getColumn()+1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//north-west
		p.setValues(position.getRow()-1,position.getColumn()-1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//South-east
		p.setValues(position.getRow()+1,position.getColumn()+1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//South-west
		p.setValues(position.getRow()+1,position.getColumn()-1);
		if(getBoard().positionExists(p)&&canMove(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		return mat;
	}

}
