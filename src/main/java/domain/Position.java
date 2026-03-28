package domain;

import java.util.Objects;

public class Position {
    private final Row row;

    private final Column column;
    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position next(Direction direction) {
        final int nextRow = this.row.value() + direction.dRow();
        final int nextCol = this.column.value() + direction.dColumn();

        return Position.of(nextRow, nextCol);
    }
    public static Position of(int row, int column) {
        return new Position(new Row(row), new Column(column));
    }

    public int row() {
        return row.value();
    }

    public int column() {
        return column.value();
    }

    public boolean isInsideBoard() {
        return row() >= 0 && row() <= 9 && column() >= 0 && column() <= 8;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row() + "," + column() + ")";
    }

}
