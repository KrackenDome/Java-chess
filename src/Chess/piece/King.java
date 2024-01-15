package Chess.piece;

import Board.GameBoard;
import Board.Position;
import Chess.ChessMatch;
import Chess.ChessPiece;
import Chess.Colour;

public class King extends ChessPiece {
    // Private field to store the reference to the ChessMatch
    private ChessMatch chessMatch;

    // Constructor to initialize a King with a specified game board, color, and ChessMatch
    public King(GameBoard gameBoard, Colour colour, ChessMatch chessMatch) {
        super(gameBoard, colour);
        this.chessMatch = chessMatch;
    }

    // Override of the toString() method to provide a string representation of the King
    @Override
    public String toString() {
        return "♔"; // Unicode representation of the King chess piece
    }

    // Private method to check if the King can move to a specified position
    private boolean canMove(Position position) {
        ChessPiece p = (ChessPiece) gameBoard.pieces(position);
        return p == null || p.getColour() != getColour();
    }

    // Private method to test if castling with the rook at a specified position is possible
    private boolean testRookCastling(Position position) {
        ChessPiece p = (ChessPiece) getBoard().pieces(position);
        return p != null && p instanceof Rook && p.getColour() == getColour() && p.getMoveCount() == 0;
    }

    // Override of the possibleMoves() method to calculate and return possible moves for the King
    @Override
    public boolean[][] possibleMoves() {
        boolean[][] matrix = new boolean[gameBoard.getRows()][gameBoard.getColumns()];

        Position p = new Position(0, 0);

        // Calculate possible moves in various directions
        int[][] directions = {
                {0, -1}, {-1, -1}, {-1, 0}, {-1, 1},
                {0, 1}, {1, 1}, {1, 0}, {1, -1}
        };

        for (int[] dir : directions) {
            p.setValues(position.getRow() + dir[0], position.getColumn() + dir[1]);
            if (gameBoard.positionExists(p) && canMove(p)) {
                matrix[p.getRow()][p.getColumn()] = true;
            }
        }

        // Special move: Castling
        if (getMoveCount() == 0 && !chessMatch.getCheck()) {
            // Kingside castling
            Position posT1 = new Position(position.getRow(), position.getColumn() + 3);
            if (testRookCastling(posT1)) {
                Position p1 = new Position(position.getRow(), position.getColumn() + 1);
                Position p2 = new Position(position.getRow(), position.getColumn() + 2);
                if (getBoard().pieces(p1) == null && getBoard().pieces(p2) == null) {
                    matrix[position.getRow()][position.getColumn() + 2] = true;
                }
            }
            // Queenside castling
            Position posT2 = new Position(position.getRow(), position.getColumn() - 4);
            if (testRookCastling(posT2)) {
                Position p1 = new Position(position.getRow(), position.getColumn() - 1);
                Position p2 = new Position(position.getRow(), position.getColumn() - 2);
                Position p3 = new Position(position.getRow(), position.getColumn() - 3);
                if (getBoard().pieces(p1) == null && getBoard().pieces(p2) == null && getBoard().pieces(p3) == null) {
                    matrix[position.getRow()][position.getColumn() - 2] = true;
                }
            }
        }

        return matrix;
    }
}
