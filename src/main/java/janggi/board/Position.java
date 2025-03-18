package janggi.board;

public class Position {

    private final char column;
    private final int row;

    public Position(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position minusColumn() {
        char newColumn = (char) (column - 1);
        return new Position(newColumn, row);
    }

    public Position plusColumn() {
        char newColumn = (char) (column + 1);
        return new Position(newColumn, row);
     }

    public Position plusRow() {
        return new Position(column, row + 1);
    }
}
