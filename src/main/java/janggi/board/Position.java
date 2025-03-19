package janggi.board;

import java.util.Objects;

public class Position {

    private final char column;
    private final int row;

    public Position(char column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position plusRows(int difference) {
        return new Position(column, row + difference);
    }

    public Position plusColumns(int difference) {
        return new Position((char) (column + difference), row);
    }

    public Position plus(int columnDifference, int rowDifference) {
        return new Position((char) (column + columnDifference), row + rowDifference);
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

    public Position minusRow() {
        return new Position(column, row - 1);
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Position position = (Position) object;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }
}
