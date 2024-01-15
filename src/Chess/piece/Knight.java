package Chess.piece;

import Board.GameBoard;
import Board.Position;
import Chess.ChessPiece;
import Chess.Colour;

public class Knight extends ChessPiece {

    // Constructor to initialize a Knight with a specified game board and color
    public Knight(GameBoard gameBoard, Colour colour) {
        super(gameBoard, colour);
    }

    // Override of the toString() method to provide a string representation of the Knight
    @Override
    public String toString() {
        return "♞"; // Unicode representation of the Knight chess piece
    }

    // Private method to check if the Knight can move to a specified position
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) getBoard().pieces(position);
        return p == null || p.getColour() != getColour();
    }

    // Override of the possibleMoves() method to calculate and return possible moves for the Knight
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        // Calculate possible moves in the knight's L-shaped pattern
        int[][] directions = {
                {-1, -2}, {-2, -1}, {-2, 1}, {-1, 2},
                {1, 2}, {2, 1}, {2, -1}, {1, -2}
        };

        for (int[] dir : directions) {
            p.setValues(position.getRow() + dir[0], position.getColumn() + dir[1]);
            if (getBoard().positionExists(p) && canMove(p)) {
                mat[p.getRow()][p.getColumn()] = true;
            }
        }

        return mat;
    }
}
