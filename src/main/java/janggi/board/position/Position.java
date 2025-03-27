package janggi.board.position;

import java.util.Objects;

public class Position {
    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(Row row, Column column) {
        this.column = column;
        this.row = row;
    }

    public boolean isColumnDifferencePositive(Position other) {
        return subtractColumn(other) > 0;
    }

    public boolean isColumnDifferenceNegative(Position other) {
        return subtractColumn(other) < 0;
    }

    public boolean isRowDifferencePositive(Position other) {
        return subtractRow(other) > 0;
    }

    public boolean isRowDifferenceNegative(Position other) {
        return subtractRow(other) < 0;
    }

    public int subtractColumn(Position other) {
        return column.subtract(other.column);
    }

    public int subtractRow(Position other) {
        return row.subtract(other.row);
    }

    public Position up() {
        return new Position(column, row.up());
    }

    public Position down() {
        return new Position(column, row.down());
    }

    public Position left() {
        return new Position(column.down(), row);
    }

    public Position right() {
        return new Position(column.up(), row);
    }

    public Position leftUp() {
        return new Position(column.down(), row.up());
    }

    public Position rightUp() {
        return new Position(column.up(), row.up());
    }

    public Position leftDown() {
        return new Position(column.down(), row.down());
    }

    public Position rightDown() {
        return new Position(column.up(), row.down());
    }

    public boolean canUp() {
        return row.canUp();
    }

    public boolean canDown() {
        return row.canDown();
    }

    public boolean canLeft() {
        return column.canDown();
    }

    public boolean canRight() {
        return column.canUp();
    }

    public boolean canLeftUp() {
        return column.canDown() && row.canUp();
    }

    public boolean canRightUp() {
        return column.canUp() && row.canUp();
    }

    public boolean canLeftDown() {
        return column.canDown() && row.canDown();
    }

    public boolean canRightDown() {
        return column.canUp() && row.canDown();
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
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
