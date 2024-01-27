package Chess;

import Board.GameBoard;
import Board.Pieces;
import Board.Position;
import Chess.piece.*;

public abstract class ChessPiece extends Pieces {

    // Private fields
    private final Colour colour; // The colour of the chess piece (WHITE or BLACK)
    private int moveCount; // The number of moves the chess piece has made

    // Constructor for ChessPiece
    public ChessPiece(GameBoard board, Colour colour) {
        super(board); // Call the constructor of the superclass 'Pieces'
        this.colour = colour;
    }

    // Getter method for the colour of the chess piece
    public Colour getColour() {
        return colour;
    }

    // Getter method for the chess position in ChessPosition format
    public ChessPosition getChessPosition() {
        return ChessPosition.fromPosition(position);
    }

    // Getter method for the move count of the chess piece
    public int getMoveCount() {
        return moveCount;
    }

    // Method to increase the move count of the chess piece
    public void increaseMoveCount() {
        moveCount++;
    }

    // Method to decrease the move count of the chess piece
    public void decreaseMoveCount() {
        moveCount--;
    }

    // Protected method to check if there is an opponent's piece at the given position
    protected boolean isThereOpponentPiece(Position position) {
        ChessPiece p = (ChessPiece) getBoard().pieces(position);
        return p != null && p.getColour() != colour;
    }

    // Decorator-related methods
    public void draw() {
        System.out.println("Drawing ChessPiece");
    }

    public void move(int x, int y) {
        System.out.println("Moving ChessPiece to " + x + ", " + y);
    }

    public void capture(ChessPiece other) {
        System.out.println("Capturing ChessPiece");
    }

    // Factory method to create concrete ChessPiece instances based on type and color
    public static ChessPiece createPiece(String type, Colour colour, GameBoard board, ChessMatch chessMatch) {
        switch (type.toUpperCase()) {
            case "KING":
                return new King(board, colour, chessMatch);
            case "PAWN":
                return new Pawn(board, colour, chessMatch);
            default:
                throw new IllegalArgumentException("Invalid chess piece type: " + type);
        }
    }

    // Factory method overload for pieces that don't require ChessMatch
    public static ChessPiece createPiece(String type, Colour colour, GameBoard board) {
        switch (type.toUpperCase()) {
            case "ROOK":
                return new Rook(board, colour);
            case "BISHOP":
                return new Bishop(board, colour);
            case "KNIGHT":
                return new Knight(board, colour);
            case "QUEEN":
                return new Queen(board, colour);
            default:
                throw new IllegalArgumentException("Invalid chess piece type: " + type);
        }
    }

    // Factory method to create a KnightDecorator
    public ChessPiece createKnightDecorator(ChessPiece decoratedPiece) {
        return new KnightDecorator(decoratedPiece);
    }

    // Concrete decorator class for adding the ability to move like a knight
    public class KnightDecorator extends ChessPiece {

        private final ChessPiece decoratedPiece;

        public KnightDecorator(ChessPiece decoratedPiece) {
            super(decoratedPiece.getBoard(), decoratedPiece.getColour());
            this.decoratedPiece = decoratedPiece;
        }

        @Override
        public void draw() {
            decoratedPiece.draw();
            System.out.println("Drawing KnightDecorator");
        }

        @Override
        public void move(int x, int y) {
            System.out.println("Moving like a knight!");
            decoratedPiece.move(x, y);
        }

        @Override
        public void capture(ChessPiece other) {
            decoratedPiece.capture(other);
            System.out.println("Capturing with special knight ability");
        }

        @Override
        public boolean[][] possibleMoves() {
            return new boolean[0][];
        }
    }
}
