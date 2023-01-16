package Chess;

import Boardgame.Board;
import Boardgame.Piece;
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
	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
		
		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		Piece capturedPiece = makeMove(source,target);
		return (ChessPiece)capturedPiece;
	}
	
	private Piece makeMove(Position source, Position target) {
		Piece sourcePiece = board.removePiece(source);
		Piece capturedPiece =board.removePiece(target);
		board.placePiece(sourcePiece, target);
		return capturedPiece;
	}
	
	private void validateSourcePosition(Position source) {
		if(!board.thereIsAPiece(source)) {
			throw new ChessException("There is no piece at source position");
		}
		if(!board.piece(source).isThereAnyPossibleMoves()) {
			throw new ChessException("There is no possible moves for this piece.");
		}
	}
	
	public void placeNewPiece(char column, int row, ChessPiece piece){
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
	}
	
	private void initialSetup(){
		
		placeNewPiece('c', 1, new Rook(board, Color.WHITE));
        placeNewPiece('c', 2, new Rook(board, Color.WHITE));
        placeNewPiece('d', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 2, new Rook(board, Color.WHITE));
        placeNewPiece('e', 1, new Rook(board, Color.WHITE));
        placeNewPiece('d', 1, new King(board, Color.WHITE));

        placeNewPiece('c', 7, new Rook(board, Color.BLACK));
        placeNewPiece('c', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 7, new Rook(board, Color.BLACK));
        placeNewPiece('e', 8, new Rook(board, Color.BLACK));
        placeNewPiece('d', 8, new King(board, Color.BLACK));
	}
	
}
