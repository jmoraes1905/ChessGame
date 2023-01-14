package Boardgame;

public class Board {
	
	private int rows;
	private int columns;
	private Piece[][] pieces;
	
	public Board(int rows, int columns) {
		if(rows<1||columns<1) {
			throw new BoardException("Error creating board. It's necessary at least 1 row and one column");
		}
		else if(rows!=columns) {
			throw new BoardException("Error creating board. The number of rows must be the same number of columns");
		}
		this.rows = rows;
		this.columns = columns;
		pieces = new Piece[rows][columns];
	}
	public int getRows() {
		return rows;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Piece piece(int row, int column) {
		if(!positionExists(row,column)) {
			throw new BoardException("Position not on the board");
		}
		return pieces[row][column];//return the piece (or null) at the position give by the row and column
	}
	
	public Piece piece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position is not on the board");
		}
		return pieces[position.getRow()][position.getColumn()];//uses the above method for auxiliary implementation
	}
	
	public void placePiece(Piece piece, Position position) {
		if(thereIsAPiece(position)) {
			throw new BoardException("There is already a piece on position "+ position);
		}
		pieces[position.getRow()][position.getColumn()] = piece;
		piece.position = position; // The pieces' position is always accessible since Piece and Board share the same package 
	}
	
	public boolean positionExists(int row, int column) {
		return row<= this.rows && row>= 0 &&column<=this.columns&&column>=0;
	}
	
	public boolean positionExists(Position position) {
		return positionExists(position.getRow(),position.getColumn());
	}
	
	public boolean thereIsAPiece(Position position) {
		if(!positionExists(position)) {
			throw new BoardException("Position is not on the board");
		}
		return piece(position)!= null;
	}
	
}
