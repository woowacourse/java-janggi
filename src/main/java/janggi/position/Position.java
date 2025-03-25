package janggi.position;

import java.util.Objects;

public record Position(Row row, Column column) {

    public Position move(int rowMovement, int columnMovement) {
        return new Position(row.move(rowMovement), column.move(columnMovement));
    }

    public boolean isSameRow(Position position) {
        return position.row.equals(row);
    }

    public boolean isSameColumn(Position position) {
        return position.column.equals(column);
    }


    public boolean isOutOfBoards() {
        return row().isOutOfBounds() || column().isOutOfBounds();
    }

    public boolean isVerticalFromPosition(Position position) {
        return isSameColumn(position);
    }

    public boolean isHorizontalFromPosition(Position position) {
        return isSameRow(position);
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
