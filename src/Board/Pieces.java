package Board;

public abstract class Pieces {
    // Protected fields to store the position and the game board to which the piece belongs
    protected Position position;
    protected GameBoard gameBoard;

    // Constructor for initializing a piece with a reference to the game board
    public Pieces(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
        this.position = null; // Position is initially set to null
    }

    // Getter method to retrieve the current position of the piece
    public Position getPosition() {
        return position;
    }

    // Setter method to set the position of the piece
    public void setPosition(Position position) {
        this.position = position;
    }

    // Protected method to retrieve the game board to which the piece belongs
    protected GameBoard getBoard() {
        return gameBoard;
    }

    // Abstract method to calculate and return a 2D boolean array representing possible moves for the piece
    public abstract boolean[][] possibleMoves();

    // Method to check if a specific position is a possible move for the piece
    public boolean possibleMove(Position position) {
        return possibleMoves()[position.getRow()][position.getColumn()];
    }

    // Method to check if there is any possible move for the piece
    public boolean isThereAnyPossibleMove() {
        boolean[][] matrix = possibleMoves();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                if (matrix[i][j]) {
                    return true;
                }
            }
        }
        return false;
    }
}