package Board;

public class GameBoard {
    // Private fields to store the number of rows, number of columns, and the 2D array of pieces
    private int rows;
    private int columns;
    private Pieces[][] pieces;

    // Constructor to initialize the GameBoard with a specified number of rows and columns
    public GameBoard(int rows, int columns) {
        // Check if the specified number of rows and columns is valid
        if (rows < 1 || columns < 1) {
            // Throw a custom exception if the input is invalid
            throw new Exception("Error creating board: there must be at least 1 row and 1 column");
        }
        // Initialize the rows, columns, and the 2D array of pieces
        this.rows = rows;
        this.columns = columns;
        this.pieces = new Pieces[rows][columns];
    }

    // Getter method to retrieve the number of rows
    public int getRows() {
        return rows;
    }

    // Getter method to retrieve the number of columns
    public int getColumns() {
        return columns;
    }

    // Method to retrieve the piece at a specific position identified by row and column indices
    public Pieces pieces(int row, int column) {
        // Check if the position is valid
        if (!positionExists(row, column)) {
            // Throw a custom exception if the position is not on the board
            throw new Exception("Position not on the board");
        }
        // Return the piece at the specified position
        return pieces[row][column];
    }

    // Method to retrieve the piece at a specific position identified by a Position object
    public Pieces pieces(Position position) {
        // Check if the position is valid
        if (!positionExists(position)) {
            // Throw a custom exception if the position is not on the board
            throw new Exception("Position not on the board");
        }
        // Return the piece at the specified position
        return pieces[position.getRow()][position.getColumn()];
    }

    // Method to place a piece on the board at a specific position
    public void placePiece(Pieces piece, Position position) {
        // Check if there is already a piece at the specified position
        if (thereIsAPiece(position)) {
            // Throw a custom exception if there is already a piece at the position
            throw new Exception("There is already a piece on position " + position);
        }
        // Place the piece on the board and update its position
        pieces[position.getRow()][position.getColumn()] = piece;
        piece.position = position;
    }

    // Method to remove a piece from the board at a specific position
    public Pieces removePiece(Position position) {
        // Check if the position is valid
        if (!positionExists(position)) {
            // Throw a custom exception if the position is not on the board
            throw new Exception("Position not on the board");
        }
        // Check if there is a piece at the specified position
        if (pieces(position) == null) {
            // Return null if there is no piece at the position
            return null;
        }
        // Remove the piece from the board, update its position, and return the removed piece
        Pieces aux = pieces(position);
        aux.position = null;
        pieces[position.getRow()][position.getColumn()] = null;
        return aux;
    }

    // Private method to check if a position identified by row and column indices exists on the board
    private boolean positionExists(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    // Public method to check if a Position object exists on the board
    public boolean positionExists(Position position) {
        return positionExists(position.getRow(), position.getColumn());
    }

    // Public method to check if there is a piece at a specific position on the board
    public boolean thereIsAPiece(Position position) {
        // Check if the position is valid
        if (!positionExists(position)) {
            // Throw a custom exception if the position is not on the board
            throw new Exception("Position not on the board");
        }
        // Return true if there is a piece at the specified position, false otherwise
        return pieces(position) != null;
    }
}
