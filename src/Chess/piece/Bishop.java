package Chess.piece;

import Board.GameBoard;
import Board.Position;
import Chess.ChessPiece;
import Chess.Colour;

public class Bishop extends ChessPiece {

    // Constructor to initialize a Bishop with a specified game board and color
    public Bishop(GameBoard gameBoard, Colour colour) {
        super(gameBoard, colour);
    }

    // Override of the toString() method to provide a string representation of the Bishop
    @Override
    public String toString() {
        return "♗"; // Unicode representation of the Bishop chess piece
    }

    // Override of the possibleMoves() method to calculate and return possible moves for the Bishop
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[gameBoard.getRows()][gameBoard.getColumns()];

        Position p = new Position(0, 0);

        // Calculate possible moves in the left-up direction
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Calculate possible moves in the up-right direction
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Calculate possible moves in the right-down direction
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Calculate possible moves in the down-left direction
        p.setValues(position.getRow() + 1, position.getColumn() - 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() - 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        return matrix;
    }
}