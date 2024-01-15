package App;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.ChessPosition;
import Chess.Colour;

public class UI {
    // ANSI color codes for console text and background colors
    // These codes are used to format the console output
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // ANSI color codes for console background colors
    public static final String ANSI_BORDER = "\u001B[100m";
    public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    // Clears the console screen
    public static void clearScreen() {
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    // Reads a chess position from user input
    public static ChessPosition readChessPosition(Scanner sc) {
        try {
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        } catch (RuntimeException e) {
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    // Prints the current state of the chess match
    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> captured) {
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(captured);
        System.out.println();
        System.out.println("Turn : " + chessMatch.getTurn());
        if (!chessMatch.getCheckMate()) {
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if (chessMatch.getCheck()) {
                System.out.println("CHECK!");
            }
        } else {
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }
    }

    // Prints the chess board with the pieces
    public static void printBoard(ChessPiece[][] pieces) {
        System.out.println(ANSI_BORDER + "_____________________" + ANSI_RESET);
        for (int i = 0; i < pieces.length; i++) {
            System.out.print((8 - i) + ANSI_CYAN + " | " + ANSI_RESET);
            for (int j = 0; j < pieces[i].length; j++) {
                printPiece(pieces[i][j], false);
            }
            System.out.println(ANSI_CYAN + "|" + ANSI_RESET);
        }
        System.out.println(ANSI_BORDER + "____________________" + ANSI_RESET);
        System.out.println("a b c d e f g h");
    }

    // Prints the chess board with possible moves highlighted
    public static void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves) {
        int size = pieces.length;

        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print("___");
        }
        System.out.println();

        for (int i = 0; i < size; i++) {
            System.out.print((size - i) + " |");

            for (int j = 0; j < size; j++) {
                printPiece(pieces[i][j], possibleMoves[i][j]);
            }
            System.out.println("|");
        }

        // Print the bottom border
        System.out.print("  ");
        for (int i = 0; i < size; i++) {
            System.out.print("---");
        }
        System.out.println();

        // Print the column labels
        System.out.print("   ");
        for (int i = 0; i < size; i++) {
            System.out.print((char) ('a' + i) + " ");
        }
        System.out.println();
    }

    // Prints a chess piece with optional background highlighting
    private static void printPiece(ChessPiece piece, boolean background) {
        if (background) {
            System.out.print(ANSI_WHITE_BACKGROUND);
        }
        if (piece == null) {
            System.out.print("-" + ANSI_RESET);
        } else {
            if (piece.getColour() == Colour.WHITE) {
                System.out.print(ANSI_WHITE + piece + ANSI_RESET);
            } else {
                System.out.print(ANSI_BLACK + piece + ANSI_RESET);
            }
        }
        System.out.print(" ");
    }

    // Prints the list of captured pieces for both white and black players
    private static void printCapturedPieces(List<ChessPiece> captured) {
        List<ChessPiece> white = captured.stream().filter(x -> x.getColour() == Colour.WHITE)
                .collect(Collectors.toList());
        List<ChessPiece> black = captured.stream().filter(x -> x.getColour() == Colour.BLACK)
                .collect(Collectors.toList());
        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_BLACK);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
}
