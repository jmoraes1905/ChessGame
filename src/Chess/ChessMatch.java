package Chess;

import Boardgame.Board;
import Boardgame.Position;
import Chess.pieces.King;
import Chess.pieces.Rook;

public class ChessMatch {

	private Board board;

	public ChessMatch() {
		this.board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces(){
		ChessPiece [][] mat = new ChessPiece[this.board.getRows()][this.board.getColumns()];
		for(int i=0;i<board.getRows();i++) {
			for(int j =0;j<board.getColumns();j++) {
				mat[i][j] = (ChessPiece) board.piece(i,j);
				
			}
		}
		return mat;	
	}
	/*Notice that since the pieces are placed on the board accordingly to the matrix position
	 * instead of the chess position, we must create a method of placement that converts
	 * the chess_
	 * position, set by the player, to a matrix_position, read by the Board Layer
	 */
	
	public void placeNewPiece(char column, int row, ChessPiece piece){
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
	}
	
	private void initialSetup(){
		
		placeNewPiece('b',1,new Rook(board,Color.WHITE));
		placeNewPiece('e',8,new King(board,Color.BLACK));
		placeNewPiece('h',8,new Rook(board,Color.BLACK));
	}
	
}
