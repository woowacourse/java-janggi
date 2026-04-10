package domain;

import java.util.Comparator;
import java.util.Objects;

public class Position implements Comparable<Position> {
    private final Row row;
    private final Column column;

    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position next(Direction direction) {
        int nextRow = this.row.value() + direction.dRow();
        int nextCol = this.column.value() + direction.dColumn();

        return Position.of(nextRow, nextCol);
    }

    public Position goUp() {
        return next(Direction.NORTH);
    }

    public Position goDown() {
        return next(Direction.SOUTH);
    }

    public Position goLeft() {
        return next(Direction.WEST);
    }

    public Position goRight() {
        return next(Direction.EAST);
    }

    public Position goUpLeft() {
        return next(Direction.NORTH_WEST);
    }

    public Position goDownRight() {
        return next(Direction.SOUTH_EAST);
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
        return row.isInsideBoard() && column.isInsideBoard();
    }

    public boolean isWithin(Position topLeft, Position bottomRight) {
        return row() >= topLeft.row() && row() <= bottomRight.row()
                && column() >= topLeft.column() && column() <= bottomRight.column();
    }

    public boolean sharesRowOrColumnWith(Position other) {
        if (row() == other.row()) {
            return true;
        }
        return column() == other.column();
    }

    @Override
    public int compareTo(Position other) {
      return Comparator.comparingInt(Position::row)
                       .thenComparingInt(Position::column)
                       .compare(this, other);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
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
