package janggi.board;

import java.util.Objects;

public class Position {
    private final int column;
    private final int row;

    public Position(int column, int row) {
        this.column = column;
        this.row = row;
    }

    public Position(String column, String row) {
        this(Integer.parseInt(column), Integer.parseInt(row));
    }

    public Position up() {
        return new Position(column, row + 1);
    }

    public Position down() {
        return new Position(column, row - 1);
    }

    public Position left() {
        return new Position(column - 1, row);
    }

    public Position right() {
        return new Position(column + 1, row);
    }

    public Position leftUp() {
        return new Position(column - 1, row + 1);
    }

    public Position rightUp() {
        return new Position(column + 1, row + 1);
    }

    public Position leftDown() {
        return new Position(column - 1, row - 1);
    }

    public Position rightDown() {
        return new Position(column + 1, row - 1);
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

    @Override
    public String toString() {
        return "" + column + row;
    }
}
