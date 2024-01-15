package Chess;

import Board.Position;

public class ChessPosition {

    // Private fields
    private char column; // The column of the chess position (a to h)
    private int row;     // The row of the chess position (1 to 8)

    // Constructor for ChessPosition
    public ChessPosition(char column, int row) {
        // Check if the given column and row values are within valid chess board ranges
        if (column < 'a' || column > 'h' || row < 1 || row > 8) {
            throw new ChessException("Error instantiating ChessPosition. Valid values are from a1 to h8.");
        }
        this.column = column;
        this.row = row;
    }

    // Getter method for the column of the chess position
    public char getColumn() {
        return column;
    }

    // Getter method for the row of the chess position
    public int getRow() {
        return row;
    }

    // Protected method to convert ChessPosition to Position in the board
    protected Position toPosition() {
        // Convert algebraic notation to board position (using 'a' as 0 and 'h' as 7)
        return new Position(8 - row, column - 'a');
    }

    // Protected static method to create ChessPosition from a Position on the board
    protected static ChessPosition fromPosition(Position position) {
        // Convert board position to algebraic notation
        return new ChessPosition((char) ('a' + position.getColumn()), 8 - position.getRow());
    }

    // Override the toString method to represent the ChessPosition as a string
    @Override
    public String toString() {
        return "" + column + row;
    }
}
