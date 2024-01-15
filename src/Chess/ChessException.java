package Chess;

import Board.Exception;

public class ChessException extends Exception {

    private static final long serialVersionUID = 1L;

    // Constructor to initialize a ChessException with a specified error message
    public ChessException(String msg) {
        super(msg);
    }

}
