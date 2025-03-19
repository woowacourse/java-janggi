package janggi.board;

import java.util.Objects;

public class Position {

    private final int column;
    private final int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position plus(int columnDifference, int rowDifference) {
        return new Position(column + columnDifference, row + rowDifference);
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
