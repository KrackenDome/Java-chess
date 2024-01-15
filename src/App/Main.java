package App;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import Chess.ChessException;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;

public class Main {

    public static void main(String[] args) {

        // Create a scanner object to read user input
        Scanner sc = new Scanner(System.in);

        // Create a ChessMatch object to represent the chess game
        ChessMatch chessMatch = new ChessMatch();

        // Create an empty list to store captured chess pieces
        List<ChessPiece> captured = new ArrayList<>();

        // Loop until the game is not in checkmate
        while (!chessMatch.getCheckMate()) {
            try {
                // Clear the screen and print the current state of the game
                UI.clearScreen();
                UI.printMatch(chessMatch, captured);
                System.out.println();
                System.out.print("Source: ");

                // Read the source position of the chess piece from the user
                ChessPosition source = UI.readChessPosition(sc);

                // Calculate the possible moves for the selected piece
                boolean[][] possibleMoves = chessMatch.possibleMoves(source);

                // Clear the screen and print the chessboard with possible moves highlighted
                UI.clearScreen();
                UI.printBoard(chessMatch.getPieces(), possibleMoves);

                System.out.println();
                System.out.print("Target: ");

                // Read the target position of the chess piece from the user
                ChessPosition target = UI.readChessPosition(sc);

                // Move the chess piece from the source position to the target position
                ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                // If a piece is captured, add it to the captured list
                if (capturedPiece != null) {
                    captured.add(capturedPiece);
                }

                // If a pawn is promoted, prompt the user to enter the type of piece to promote to
                if (chessMatch.getPromoted() != null) {
                    System.out.println("Enter piece for promotion (♗/♞/♖/♛): ");
                    String type = sc.nextLine().toUpperCase();
                    while (!type.equals("♗") && !type.equals("♞") && !type.equals("♖") & !type.equals("♛")) {
                        System.out.println("Invalid value! Enter piece for promotion (♗/♞/♖/♛): ");
                        type = sc.nextLine().toUpperCase();
                    }
                    chessMatch.replacePromotedPiece(type);
                }
            }
            // Catch and handle any ChessException that may occur during the game
            catch (ChessException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
            // Catch and handle any InputMismatchException that may occur during user input
            catch (InputMismatchException e) {
                System.out.println(e.getMessage());
                sc.nextLine();
            }
        }

        // Clear the screen and print the final state of the game
        UI.clearScreen();
        UI.printMatch(chessMatch, captured);
    }

}