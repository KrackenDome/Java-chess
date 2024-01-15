package Board;

public class Position {
    // Private fields to store the row and column indices of the position
    private int row;
    private int column;

    // Constructor to initialize a position with specified row and column indices
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // Method to set the row and column indices of the position
    public void setValues(int row, int column) {
        this.row = row;
        this.column = column;
    }

    // Getter method to retrieve the row index of the position
    public int getRow() {
        return row;
    }

    // Setter method to set the row index of the position
    public void setRow(int row) {
        this.row = row;
    }

    // Getter method to retrieve the column index of the position
    public int getColumn() {
        return column;
    }

    // Setter method to set the column index of the position
    public void setColumn(int column) {
        this.column = column;
    }

    // Override of the toString() method to provide a string representation of the position
    @Override
    public String toString() {
        return row + ", " + column;
    }
}
