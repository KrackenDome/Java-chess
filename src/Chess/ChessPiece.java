package Chess;

import Board.GameBoard;
import Board.Pieces;
import Board.Position;

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
}
