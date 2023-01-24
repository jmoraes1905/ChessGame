package Chess.pieces;

import Boardgame.Board;
import Boardgame.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Color;

public class Pawn extends ChessPiece {
	
	private ChessMatch chessMatch;
	
	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
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
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2)&& getMoveCount()==0) {
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
			
			//en passant white pawns
			if(position.getRow()==3) {
				Position left = new Position(position.getRow(),position.getColumn()-1);
				if(getBoard().positionExists(left)&&isThereOpponentPiece(left)&&getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()-1][left.getColumn()]=true;
				}
				Position right = new Position(position.getRow(),position.getColumn()+1);
				if(getBoard().positionExists(right)&&isThereOpponentPiece(right)&&getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()-1][right.getColumn()]=true;
				}
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
			if(getBoard().positionExists(p)&& !getBoard().thereIsAPiece(p)&& getBoard().positionExists(p2)&&!getBoard().thereIsAPiece(p2) && getMoveCount()==0) {
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
			
			//en passant black pawns
			if(position.getRow()==4) {
				Position left = new Position(position.getRow(),position.getColumn()-1);
				if(getBoard().positionExists(left)&&isThereOpponentPiece(left)&&getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow()+1][left.getColumn()]=true;
				}
				Position right = new Position(position.getRow(),position.getColumn()+1);
				if(getBoard().positionExists(right)&&isThereOpponentPiece(right)&&getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow()+1][right.getColumn()]=true;
				}
			}
		}
		
		return mat;

	}
	
	@Override
	public String toString() {
		return "P";
	}

}
