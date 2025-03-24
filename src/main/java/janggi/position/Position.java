package janggi.position;

import java.util.Objects;

public record Position(Row row, Column column) {

    public Position move(int rowMovement, int columnMovement) {
        return new Position(row.move(rowMovement), column.move(columnMovement));
    }

    public boolean isOutOfBoards() {
        return false;
    }

    public int getRow() {
        return row.value();
    }

    public int getColumn() {
        return column.value();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "[" + row + "," + column + "]";
    }
}
