package Chess.pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessPiece;
import Chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
		
	}

	@Override
	public String toString() {
		return "R";
	}

//	@Override
/*	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[getBoard().getRows()][getBoard().getColumns()];
		Position p = new Position(0,0);
		//Check above the current position
		p.setValues(this.position.getRow()-1,this.position.getColumn());
		while(getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(this.position.getRow()-1);
		}
		if(getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		//Check left side of the current position
		p.setValues(this.position.getRow(),this.position.getColumn()-1);
		while(getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setColumn(this.position.getColumn()-1);
		}
		if(getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
		}
		
		//Check right side of the current position
		p.setValues(this.position.getRow(),this.position.getColumn()+1);
		while(getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
		mat[p.getRow()][p.getColumn()] = true;
		p.setColumn(this.position.getColumn()+1);
			}
		if(getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			}
		//Check bellow the current position
		p.setValues(this.position.getRow()+1,this.position.getColumn());
		while(getBoard().positionExists(p)&&!getBoard().thereIsAPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
			p.setRow(this.position.getRow()+1);
			}
		if(getBoard().positionExists(p)&&isThereOpponentPiece(p)) {
			mat[p.getRow()][p.getColumn()] = true;
				}	
		
		return mat;
	}
	*/
	@Override
	public boolean[][] possibleMoves() {
		boolean mat[][] = new boolean[this.getBoard().getRows()][this.getBoard().getColumns()];
		return mat;
	}
}
