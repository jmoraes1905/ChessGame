package Chess;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import Boardgame.Board;
import Boardgame.BoardException;
import Boardgame.Piece;
import Boardgame.Position;
import Chess.pieces.Bishop;
import Chess.pieces.King;
import Chess.pieces.Knight;
import Chess.pieces.Pawn;
import Chess.pieces.Queen;
import Chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
		
	public ChessMatch() {
		this.board = new Board(8,8);
		turn =1;
		currentPlayer = Color.WHITE;
		check = false; // Already false by default
		initialSetup();
	}
		
	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}
	
	public boolean getCheck() {
		return check;
	}
	
	public boolean getCheckMate() {
		return checkMate;
	}
	
	public ChessPiece getEnPassantVulnerable() {
		return this.enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return this.promoted;
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
	
	public boolean[][] PossibleMoves(ChessPosition sourcePosition){
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);
		return board.piece(position).possibleMoves();
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
		System.out.println(source);
		validateTargetPosition(source, target);
		System.out.println(target);
		Piece capturedPiece = makeMove(source,target);
		
		//Promotion must occur before testing for check, since we are changing the game dynamics
		
		ChessPiece movedPiece = (ChessPiece)board.piece(target);
		promoted = null;
		if(movedPiece instanceof Pawn) {
			if(movedPiece.getColor()==Color.WHITE && movedPiece.getChessPosition().toPosition().getRow() == 0 || movedPiece.getColor()==Color.BLACK && movedPiece.getChessPosition().toPosition().getRow() == 7) {
				promoted = (ChessPiece)board.piece(target);
				promoted = replacePromotedPiece("Q");
			}
		}
		
		if(testCheck(currentPlayer)) {
			undoMove(source, target, capturedPiece);
			throw new ChessException("You cannot put yourself in check");
		}
		check = testCheck(opponent(currentPlayer))? true : false;
		
		if(testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		}
		else {
			nextTurn();
		}
		//Test en Passant vulnerability
		
		if(movedPiece instanceof Pawn && (target.getRow() == source.getRow()+2 || target.getRow() == source.getRow()-2)) {
			enPassantVulnerable = movedPiece;
		}
		
		else {
			enPassantVulnerable = null;
		}
		
		return (ChessPiece)capturedPiece;
	}
	
	public ChessPiece replacePromotedPiece (String type) {
		if(promoted == null) {
			throw new IllegalStateException("There is no piece to be promoted");
		}
		
		if(!type.equals("B") && !type.equals("N")&&!type.equals("Q")&&!type.equals("R")) {
			throw new InvalidParameterException("Invalid piece for promotion");
		}
		
		Position pos = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(pos);
		capturedPieces.add(p);
		piecesOnTheBoard.remove(p);
		
		ChessPiece newPiece = newPiece(type,promoted.getColor());
		board.placePiece(newPiece, pos);
		piecesOnTheBoard.add(newPiece);
		
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if(type.equals("B")) return new Bishop (board,color);
		else if(type.equals("N")) return new Knight (board,color);
		else if(type.equals("Q")) return new Queen (board,color);
		return new Rook (board,color);
	}
	private Piece makeMove(Position source, Position target) {
		ChessPiece sourcePiece = (ChessPiece)board.removePiece(source);
		sourcePiece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(sourcePiece, target);
		if(capturedPiece!= null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		
		}
		//#rook configuration
		//king side rook
		if (sourcePiece instanceof King && target.getColumn() == source.getColumn()+2) {
			Position sourceT = new Position(source.getRow(),source.getColumn()+3);
			Position targetT = new Position(source.getRow(),source.getColumn()+1);
			ChessPiece Rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(Rook, targetT);
			Rook.increaseMoveCount();
		}
		//queen side rook
		else if(sourcePiece instanceof King && target.getColumn() == source.getColumn()-3) {
			Position sourceT = new Position(source.getRow(),source.getColumn()-4);
			Position targetT = new Position(source.getRow(),source.getColumn()-1);
			ChessPiece Rook = (ChessPiece)board.removePiece(sourceT);
			board.placePiece(Rook, targetT);
			Rook.increaseMoveCount();
		}
		
		//en passant configuration
		if(sourcePiece instanceof Pawn) {
			//if a pawn moves through diagonal but captured Piece = null, we have an en passant
			if(source.getColumn()!=target.getColumn()&& capturedPiece==null) {
				Position pawnPosition;
				if(sourcePiece.getColor()==Color.WHITE) {
					pawnPosition = new Position(target.getRow()+1,target.getColumn());
				}
				else {
					pawnPosition = new Position(target.getRow()-1,target.getColumn());
				}
				
				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}
		}
		return capturedPiece;
	}
	
	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece p = (ChessPiece)board.removePiece(target);
		p.decreaseMoveCount();
		board.placePiece(p, source);
		if(capturedPiece!=null) {
			board.placePiece(capturedPiece, target);
			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}
		if (p instanceof King && target.getColumn() == source.getColumn()+2) {
			Position sourceT = new Position(source.getRow(),source.getColumn()+3);
			Position targetT = new Position(source.getRow(),source.getColumn()+1);
			ChessPiece Rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(Rook, sourceT);
			Rook.decreaseMoveCount();
		}
		//queen side rook
		else if(p instanceof King && target.getColumn() == source.getColumn()-3) {
			Position sourceT = new Position(source.getRow(),source.getColumn()-4);
			Position targetT = new Position(source.getRow(),source.getColumn()-1);
			ChessPiece Rook = (ChessPiece)board.removePiece(targetT);
			board.placePiece(Rook, sourceT);
			Rook.decreaseMoveCount();
		}
		
		//en passant configuration
		
				if(p instanceof Pawn) {
					//if a pawn moves through diagonal but captured Piece = null, we have an en passant
					if(source.getColumn()!=target.getColumn()&& capturedPiece== enPassantVulnerable) {
						ChessPiece pawn = (ChessPiece)board.removePiece(target);
						Position pawnPosition;
						if(p.getColor()==Color.WHITE) {
							pawnPosition = new Position(3,target.getColumn());
						}
						else {
							pawnPosition = new Position(4,target.getColumn());
						}
						board.placePiece(pawn, pawnPosition);
						capturedPiece = board.removePiece(pawnPosition);
					}
					
				}
			
	}
	private void validateSourcePosition(Position source) {
		if(!board.thereIsAPiece(source)) {
			throw new ChessException("There is no piece at source position");
		}
		if(currentPlayer!=((ChessPiece)board.piece(source)).getColor()) { //Avoid that players control adversaries' pieces
			throw new ChessException("This piece aint yours!");
		}
		if(!board.piece(source).isThereAnyPossibleMoves()) {
			throw new ChessException("There is no possible moves for this piece.");
		}
		System.out.println(source);
	}
	
	private void validateTargetPosition(Position source, Position target) {
		if(!board.piece(source).possibleMove(target)) {
			throw new BoardException("Invalid target position!");
		}
		System.out.println(target);
	}
	
	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE)? Color.BLACK : Color.WHITE; 
	}
	
	private Color opponent(Color color) {
		return (color ==color.WHITE)? Color.BLACK : Color.WHITE;
	}
	
	private ChessPiece king(Color color){
		List<Piece> list = piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for (Piece p: list) {
			if(p instanceof King) {
				return (ChessPiece)p;
			}
			
		}
		
		throw new IllegalStateException("There is no " + color + "king on the board");
	}
	
	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor()!=color).collect(Collectors.toList());
		for(Piece p: opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			/*for(int i =0;i<mat.length;i++) { //Tests every position of the matrix
				for(int j=o;j<mat.length;j++) {
					if (mat[i][j] = kingPosition) {
						return true;
					}
				}
			}*/
			if(mat[kingPosition.getRow()][kingPosition.getColumn()]) { //Just tests the king position
				return true;
			}
		}
		return false;
	}
	
	private boolean testCheckMate(Color color) {
		if(!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x->((ChessPiece)x).getColor() == color).collect(Collectors.toList());
		for(Piece p:list) {
			boolean[][] mat = p.possibleMoves();
			for(int i=0;i<mat.length;i++) {
				for(int j=0; j<mat.length;j++) {
					if(mat[i][j]) {
					// If one possible move can undo a check, then it's not check mate
						Position source = ((ChessPiece)p).getChessPosition().toPosition();
						Position target = new Position(i,j);
						Piece capturedPiece = makeMove(source,target);
						boolean testCheck = testCheck(color);
						undoMove(source, target, capturedPiece);
						if(!testCheck) {
							return false;
						}
					}
				}
			}
		}
		
		return true;
	}

	public void placeNewPiece(char column, int row, ChessPiece piece){
		board.placePiece(piece, new ChessPosition(column,row).toPosition());
		piecesOnTheBoard.add(piece);
	}
	
	private void initialSetup(){
		
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b',1,  new Knight(board,Color.WHITE));
		placeNewPiece('c',1,  new Bishop(board,Color.WHITE));
		placeNewPiece('d',1,  new Queen (board,Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f',1,  new Bishop(board,Color.WHITE));
        placeNewPiece('g',1,  new Knight(board,Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE,this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE,this));
        
        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b',8,  new Knight(board,Color.BLACK));
        placeNewPiece('c',8,  new Bishop(board,Color.BLACK));
        placeNewPiece('d',8,  new Queen (board,Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f',8,  new Bishop(board,Color.BLACK));
        placeNewPiece('g',8,  new Knight(board,Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK,this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK,this));
	}
	
}
