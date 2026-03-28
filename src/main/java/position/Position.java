package position;

import java.util.Objects;
import pieces.Side;

public class Position {

    private final Row row;
    private final Column column;

    public Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position(int row, int column) {
        this.row = new Row(row);
        this.column = new Column(column);
    }

    public int row() {
        return row.index();
    }

    public int column() {
        return column.index();
    }

    public Position moveForward() {
        return new Position(row.up(), column);
    }

    public Position moveForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column);
        }
        return new Position(row.down(), column);
    }

    public Position moveBack() {
        return new Position(row.down(), column);
    }

    public Position moveBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column);
        }
        return new Position(row.up(), column);
    }

    public Position moveRight() {
        return new Position(row, column.right());
    }

    public Position moveRight(Side side) {
        if (side.isCho()) {
            return new Position(row, column.right());
        }
        return new Position(row, column.left());
    }

    public Position moveLeft() {
        return new Position(row, column.left());
    }

    public Position moveLeft(Side side) {
        if (side.isCho()) {
            return new Position(row, column.left());
        }
        return new Position(row, column.right());
    }

    public Position moveRightForward() {
        return new Position(row.up(), column.right());
    }

    public Position moveRightForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column.right());
        }
        return new Position(row.down(), column.left());
    }

    public Position moveLeftForward(Side side) {
        if (side.isCho()) {
            return new Position(row.up(), column.left());
        }
        return new Position(row.down(), column.right());
    }

    public Position moveLeftUp() {
        return new Position(row.up(), column.left());
    }

    public Position moveRightDown() {
        return new Position(row.down(), column.right());
    }

    public Position moveRightBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column.right());
        }
        return new Position(row.up(), column.left());
    }

    public Position moveLeftDown() {
        return new Position(row.down(), column.left());
    }

    public Position moveLeftBack(Side side) {
        if (side.isCho()) {
            return new Position(row.down(), column.left());
        }
        return new Position(row.up(), column.right());
    }

    public boolean isSameRow(Position departure) {
        return this.row.equals(departure.row);
    }

    public boolean isSameColumn(Position departure) {
        return this.column.equals(departure.column);
    }

    public boolean isBackRow(Position destination) {
        return this.row.isLowerThan(destination.row);
    }

    public boolean isBackRow(Position destination, Side side) {
        if (side.isCho()) {
            return this.row.isLowerThan(destination.row);
        }
        return this.row.isBiggerThan(destination.row);
    }

    public boolean isLeftColumn(Position destination) {
        return this.column.isLeft(destination.column);
    }

    public boolean isLeftColumn(Position destination, Side side) {
        if (side.isCho()) {
            return this.column.isLeft(destination.column);
        }
        return this.column.isRight(destination.column);
    }

    public boolean isGapBiggerThanOne(Position destination) {
        return row.isGapBiggerThanOne(destination.row) || column.isGapBiggerThanOne(
            destination.column);
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
        return "Position{" +
            "row=" + row +
            ", column=" + column +
            '}';
    }
}
