package domain.position;

import java.util.Objects;

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

    public boolean canMoveUp() {
        return row() < 9;
    }

    public boolean canMoveDown() {
        return row() > 0;
    }

    public boolean canMoveLeft() {
        return column() > 0;
    }

    public boolean canMoveRight() {
        return column() < 8;
    }

    public Position moveUp() {
        return new Position(row.up(), column);
    }

    public Position moveDown() {
        return new Position(row.down(), column);
    }

    public Position moveRight() {
        return new Position(row, column.right());
    }

    public Position moveLeft() {
        return new Position(row, column.left());
    }

    public Position moveRightUp() {
        return new Position(row.up(), column.right());
    }

    public Position moveLeftUp() {
        return new Position(row.up(), column.left());
    }

    public Position moveRightDown() {
        return new Position(row.down(), column.right());
    }

    public Position moveLeftDown() {
        return new Position(row.down(), column.left());
    }

    public boolean isSameRow(Position departure) {
        return this.row.equals(departure.row);
    }

    public boolean isSameColumn(Position departure) {
        return this.column.equals(departure.column);
    }

    public boolean isStraightLineTo(Position destination) {
        return isSameRow(destination) || isSameColumn(destination);
    }

    public boolean isLowerRowThan(Position destination) {
        return this.row.isLowerThan(destination.row);
    }

    public boolean isLeftColumn(Position destination) {
        return this.column.isLeft(destination.column);
    }

    public boolean isRightUp(Position destination) {
        return destination.row.index() > this.row.index()
                && destination.column.index() > this.column.index()
                && destination.row.index() - this.row.index()
                == destination.column.index() - this.column.index();
    }

    public boolean isLeftUp(Position destination) {
        return destination.row.index() > this.row.index()
                && destination.column.index() < this.column.index()
                && destination.row.index() - this.row.index()
                == this.column.index() - destination.column.index();
    }

    public boolean isRightDown(Position destination) {
        return destination.row.index() < this.row.index()
                && destination.column.index() > this.column.index()
                && this.row.index() - destination.row.index()
                == destination.column.index() - this.column.index();
    }

    public boolean isLeftDown(Position destination) {
        return destination.row.index() < this.row.index()
                && destination.column.index() < this.column.index()
                && this.row.index() - destination.row.index()
                == this.column.index() - destination.column.index();
    }

    public boolean isSingleStepDiagonalTo(Position destination) {
        return Math.abs(row() - destination.row()) == 1
                && Math.abs(column() - destination.column()) == 1;
    }

    public boolean isDoubleStepDiagonalTo(Position destination) {
        return Math.abs(row() - destination.row()) == 2
                && Math.abs(column() - destination.column()) == 2;
    }

    public boolean isMoreThanOneStepAwayFrom(Position destination) {
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
}
