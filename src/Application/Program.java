package Application;

import java.util.InputMismatchException;
import java.util.Scanner;

import Boardgame.BoardException;
import Chess.ChessException;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ChessMatch chessMatch = new ChessMatch();
		Scanner sc = new Scanner(System.in);
		while(true) {
			try {
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces());
				System.out.println();
				System.out.println("Enter source position: ");
				ChessPosition source = UI.readChessPosition(sc);
				boolean[][] possibleMoves = chessMatch.PossibleMoves(source);
				UI.clearScreen();
				UI.printBoard(chessMatch.getPieces(),possibleMoves);
				System.out.println("Enter target position: ");
				ChessPosition target = UI.readChessPosition(sc);
				
				ChessPiece capturedPiece = chessMatch.performChessMove(source,target);
				
			}
			
			catch(ChessException e) {
				System.out.println(e.getMessage());
				sc.nextLine(); //Hold the program until the user press enter
			}
			
			catch(BoardException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
			catch(InputMismatchException e) {
				System.out.println(e.getMessage());
				sc.nextLine();
			}
			
		}
		
		
	}

}
