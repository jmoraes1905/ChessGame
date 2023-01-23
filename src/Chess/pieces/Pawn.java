package Chess.pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Pawn extends ChessPiece {

	public Pawn(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p= new Position(0,0);
		
		if(getColor() == Color.WHITE) {
			//Check above position
			p.setValues(position.getRow()-1,position.getColumn());
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//Check following above position at first move
			p.setValues(position.getRow()-2,position.getColumn());//another aux variable
			Position p2 = new Position(position.getRow()-1,position.getColumn());
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//Check upper left diagonal
			p.setValues(position.getRow()-1,position.getColumn()-1);
			if(getBoard().positionExists(p)&& isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//Check upper right diagonal
			p.setValues(position.getRow()-1,position.getColumn()+1);
			if(getBoard().positionExists(p)&& isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		else {
			//Check bellow position
			p.setValues(position.getRow()+1,position.getColumn());
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			//Check following above position at first move
			p.setValues(position.getRow()+2,position.getColumn());//another aux variable
			Position p2 = new Position(position.getRow()+1,position.getColumn());
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//Check lower left diagonal
			p.setValues(position.getRow()+1,position.getColumn()-1);
			if(getBoard().positionExists(p)&& isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
			
			//Check lower right diagonal
			p.setValues(position.getRow()+1,position.getColumn()+1);
			if(getBoard().positionExists(p)&& isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getColumn()] = true;
			}
		}
		
		return mat;

	}
	
	@Override
	public String toString() {
		return "P";
	}

}
