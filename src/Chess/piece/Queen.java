package Chess.piece;

import Board.GameBoard;
import Board.Position;
import Chess.ChessPiece;
import Chess.Colour;

public class Queen extends ChessPiece {

    // Constructor to initialize a Queen with a specified game board and color
    public Queen(GameBoard gameBoard, Colour colour) {
        super(gameBoard, colour);
    }

    // Override of the toString() method to provide a string representation of the Queen
    @Override
    public String toString() {
        return "♛"; // Unicode representation of the Queen chess piece
    }

    // Override of the possibleMoves() method to calculate and return possible moves for the Queen
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[gameBoard.getRows()][gameBoard.getColumns()];

        Position p = new Position(0, 0);

        // Move left
        p.setValues(position.getRow(), position.getColumn() - 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() - 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move left+up
        p.setValues(position.getRow() - 1, position.getColumn() - 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() - 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move up
        p.setValues(position.getRow() - 1, position.getColumn());
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() - 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move up+right
        p.setValues(position.getRow() - 1, position.getColumn() + 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() - 1, p.getColumn() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move right
        p.setValues(position.getRow(), position.getColumn() + 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setColumn(p.getColumn() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move right+down
        p.setValues(position.getRow() + 1, position.getColumn() + 1);
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setValues(p.getRow() + 1, p.getColumn() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move down
        p.setValues(position.getRow() + 1, position.getColumn());
        while (gameBoard.positionExists(p) && !gameBoard.thereIsAPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
            p.setRow(p.getRow() + 1);
        }
        if (gameBoard.positionExists(p) && isThereOpponentPiece(p)) {
            matrix[p.getRow()][p.getColumn()] = true;
        }

        // Move down+left
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
