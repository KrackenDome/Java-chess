package Chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import Board.GameBoard;
import Board.Pieces;
import Board.Position;
import Chess.piece.Bishop;
import Chess.piece.King;
import Chess.piece.Knight;
import Chess.piece.Pawn;
import Chess.piece.Queen;
import Chess.piece.Rook;

public class ChessMatch {

    // Instance variables
    private static ChessMatch instance;
    private int turn;
    private Colour currentPlayer;
    private GameBoard gameBoard;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVulnerable;
    private ChessPiece promoted;
    private List<Pieces> piecesOnTheBoard = new ArrayList<>();
    private List<Pieces> capturedPieces = new ArrayList<>();

    // Constructor for initializing a new chess match
    public ChessMatch() {
        gameBoard = new GameBoard(8, 8);
        turn = 1;
        currentPlayer = Colour.WHITE;
        initialSetup();
    }

    // Static method to get the singleton instance
    public static ChessMatch getInstance() {
        if (instance == null) {
            instance = new ChessMatch();
        }
        return instance;
    }

    // Getter methods for various properties
    public int getTurn() {
        return turn;
    }

    public Colour getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean getCheck() {
        return check;
    }

    public boolean getCheckMate() {
        return checkMate;
    }

    public ChessPiece getEnPassantVulnerable() {
        return enPassantVulnerable;
    }

    public ChessPiece getPromoted() {
        return promoted;
    }

    // Getter method to retrieve the current state of the chess pieces on the board
    public ChessPiece[][] getPieces() {
        ChessPiece[][] mat = new ChessPiece[gameBoard.getRows()][gameBoard.getColumns()];
        for (int i = 0; i < gameBoard.getRows(); i++) {
            for (int j = 0; j < gameBoard.getColumns(); j++) {
                mat[i][j] = (ChessPiece) gameBoard.pieces(i, j);
            }
        }
        return mat;
    }

    // Method to calculate possible moves for a piece at a given position
    public boolean[][] possibleMoves(ChessPosition sourcePosition) {
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return gameBoard.pieces(position).possibleMoves();
    }

    // Method to perform a chess move
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Pieces capturedPiece = makeMove(source, target);

        if (testCheck(currentPlayer)) {
            undoMove(source, target, capturedPiece);
            throw new ChessException("You can't put yourself in check");
        }

        ChessPiece movedPiece = (ChessPiece) gameBoard.pieces(target);

        // #specialmove promotion
        promoted = null;
        if (movedPiece instanceof Pawn) {
            if ((movedPiece.getColour() == Colour.WHITE && target.getRow() == 0) ||
                    (movedPiece.getColour() == Colour.BLACK && target.getRow() == 7)) {
                promoted = (ChessPiece) gameBoard.pieces(target);
                promoted = replacePromotedPiece("♛");
            }
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if (testCheckMate(opponent(currentPlayer))) {
            checkMate = true;
        } else {
            nextTurn();
        }

        // #specialmove en passant
        if (movedPiece instanceof Pawn && (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
            enPassantVulnerable = movedPiece;
        } else {
            enPassantVulnerable = null;
        }

        return (ChessPiece) capturedPiece;
    }

    // Method to replace a promoted pawn with a specified piece type
    public ChessPiece replacePromotedPiece(String type) {
        if (promoted == null) {
            throw new IllegalStateException("There is no piece to be promoted");
        }
        if (!type.equals("♗") && !type.equals("♔") && !type.equals("♖") && !type.equals("♛")) {
            return promoted;
        }

        Position pos = promoted.getChessPosition().toPosition();
        Pieces p = gameBoard.removePiece(pos);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColour());
        gameBoard.placePiece(newPiece, pos);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    // Method to create a new chess piece based on a specified type and color
    private ChessPiece newPiece(String type, Colour colour) {
        if (type.equals("B")) return new Bishop(gameBoard, colour);
        if (type.equals("N")) return new Knight(gameBoard, colour);
        if (type.equals("Q")) return new Queen(gameBoard, colour);
        return new Rook(gameBoard, colour);
    }

    // Method to make a move on the chess board
    private Pieces makeMove(Position source, Position target) {
        ChessPiece p = (ChessPiece) gameBoard.removePiece(source);
        p.increaseMoveCount();
        Pieces capturedPiece = gameBoard.removePiece(target);
        gameBoard.placePiece(p, target);

        if (capturedPiece != null) {
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) gameBoard.removePiece(sourceT);
            gameBoard.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) gameBoard.removePiece(sourceT);
            gameBoard.placePiece(rook, targetT);
            rook.increaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == null) {
                Position pawnPosition;
                if (p.getColour() == Colour.WHITE) {
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                } else {
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = gameBoard.removePiece(pawnPosition);
                capturedPieces.add(capturedPiece);
                piecesOnTheBoard.remove(capturedPiece);
            }
        }

        return capturedPiece;
    }

    // Method to undo a move on the chess board
    private void undoMove(Position source, Position target, Pieces capturedPiece) {
        ChessPiece p = (ChessPiece) gameBoard.removePiece(target);
        p.decreaseMoveCount();
        gameBoard.placePiece(p, source);

        if (capturedPiece != null) {
            gameBoard.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add(capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() + 3);
            Position targetT = new Position(source.getRow(), source.getColumn() + 1);
            ChessPiece rook = (ChessPiece) gameBoard.removePiece(targetT);
            gameBoard.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2) {
            Position sourceT = new Position(source.getRow(), source.getColumn() - 4);
            Position targetT = new Position(source.getRow(), source.getColumn() - 1);
            ChessPiece rook = (ChessPiece) gameBoard.removePiece(targetT);
            gameBoard.placePiece(rook, sourceT);
            rook.decreaseMoveCount();
        }

        // #specialmove en passant
        if (p instanceof Pawn) {
            if (source.getColumn() != target.getColumn() && capturedPiece == enPassantVulnerable) {
                ChessPiece pawn = (ChessPiece) gameBoard.removePiece(target);
                Position pawnPosition;
                if (p.getColour() == Colour.WHITE) {
                    pawnPosition = new Position(3, target.getColumn());
                } else {
                    pawnPosition = new Position(4, target.getColumn());
                }
                gameBoard.placePiece(pawn, pawnPosition);
            }
        }
    }

    // Method to validate the source position of a move
    private void validateSourcePosition(Position position) {
        if (!gameBoard.thereIsAPiece(position)) {
            throw new ChessException("There is no piece on the source position");
        }
        if (currentPlayer != ((ChessPiece) gameBoard.pieces(position)).getColour()) {
            throw new ChessException("The chosen piece is not yours");
        }
        if (!gameBoard.pieces(position).isThereAnyPossibleMove()) {
            throw new ChessException("There is no possible move for the chosen piece");
        }
    }

    // Method to validate the target position of a move
    private void validateTargetPosition(Position source, Position target) {
        if (!gameBoard.pieces(source).possibleMove(target)) {
            throw new ChessException("The chosen piece can't move to the target position");
        }
    }

    // Method to proceed to the next turn
    private void nextTurn() {
        turn++;
        currentPlayer = (currentPlayer == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
    }

    // Method to determine the opponent's color
    private Colour opponent(Colour color) {
        return (color == Colour.WHITE) ? Colour.BLACK : Colour.WHITE;
    }

    // Method to retrieve the king of a specified color
    private ChessPiece king(Colour color) {
        List<Pieces> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColour() == color).collect(Collectors.toList());
        for (Pieces p : list) {
            if (p instanceof King) {
                return (ChessPiece) p;
            }
        }
        throw new IllegalStateException("There is no " + color + " king on the board");
    }

    // Method to test if a player is in check
    private boolean testCheck(Colour color) {
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<Pieces> opponentPieces = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColour() == opponent(color)).collect(Collectors.toList());
        for (Pieces p : opponentPieces) {
            boolean[][] mat = p.possibleMoves();
            if (mat[kingPosition.getRow()][kingPosition.getColumn()]) {
                return true;
            }
        }
        return false;
    }

    // Method to test if a player is in checkmate
    private boolean testCheckMate(Colour color) {
        if (!testCheck(color)) {
            return false;
        }
        List<Pieces> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColour() == color).collect(Collectors.toList());
        for (Pieces p : list) {
            boolean[][] mat = p.possibleMoves();
            for (int i = 0; i < gameBoard.getRows(); i++) {
                for (int j = 0; j < gameBoard.getColumns(); j++) {
                    if (mat[i][j]) {
                        Position source = ((ChessPiece) p).getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        Pieces capturedPiece = makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if (!testCheck) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    // Method to place a new piece on the board
    private void placeNewPiece(char column, int row, ChessPiece piece) {
        gameBoard.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    // Method for the initial setup of chess pieces on the board
    private void initialSetup() {
        placeNewPiece('a', 1, ChessPiece.createPiece("ROOK", Colour.WHITE, gameBoard));
        placeNewPiece('b', 1, ChessPiece.createPiece("KNIGHT", Colour.WHITE, gameBoard));
        placeNewPiece('c', 1, ChessPiece.createPiece("BISHOP", Colour.WHITE, gameBoard));
        placeNewPiece('d', 1, ChessPiece.createPiece("QUEEN", Colour.WHITE, gameBoard));
        placeNewPiece('e', 1, ChessPiece.createPiece("KING", Colour.WHITE, gameBoard, this));
        placeNewPiece('f', 1, ChessPiece.createPiece("BISHOP", Colour.WHITE, gameBoard));
        placeNewPiece('g', 1, ChessPiece.createPiece("KNIGHT", Colour.WHITE, gameBoard));
        placeNewPiece('h', 1, ChessPiece.createPiece("ROOK", Colour.WHITE, gameBoard));
        placeNewPiece('a', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('b', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('c', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('d', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('e', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('f', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('g', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));
        placeNewPiece('h', 2, ChessPiece.createPiece("PAWN", Colour.WHITE, gameBoard, this));


        placeNewPiece('a', 8, ChessPiece.createPiece("ROOK", Colour.BLACK, gameBoard));
        placeNewPiece('b', 8, ChessPiece.createPiece("KNIGHT", Colour.BLACK, gameBoard));
        placeNewPiece('c', 8, ChessPiece.createPiece("BISHOP", Colour.BLACK, gameBoard));
        placeNewPiece('d', 8, ChessPiece.createPiece("QUEEN", Colour.BLACK, gameBoard));
        placeNewPiece('e', 8, ChessPiece.createPiece("KING", Colour.BLACK, gameBoard, this));
        placeNewPiece('f', 8, ChessPiece.createPiece("BISHOP", Colour.BLACK, gameBoard));
        placeNewPiece('g', 8, ChessPiece.createPiece("KNIGHT", Colour.BLACK, gameBoard));
        placeNewPiece('h', 8, ChessPiece.createPiece("ROOK", Colour.BLACK, gameBoard));
        placeNewPiece('a', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('b', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('c', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('d', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('e', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('f', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('g', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));
        placeNewPiece('h', 7, ChessPiece.createPiece("PAWN", Colour.BLACK, gameBoard, this));

    }
}
