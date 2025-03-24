package model;


import java.util.List;
import java.util.Objects;

public class Position {

    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(List<Integer> columnAndRow) {
        this.column = Column.getColumnBy(columnAndRow.getFirst());
        this.row = Row.getRowBy(columnAndRow.getLast());
    }

    public boolean canMove(final Movement movement) {
        int columnAmount = movement.getColumn();
        int rowAmount = movement.getRow();
        return canMoveColumn(columnAmount) && canMoveRow(rowAmount);
    }

    public Position move(Movement movement) {
        int columnAmount = movement.getColumn();
        int rowAmount = movement.getRow();
        return new Position(moveColumn(columnAmount), moveRow(rowAmount));
    }

    private Column moveColumn(int columnAmount) {
        if (columnAmount > 0) {
            return column.up(columnAmount);
        }
        return column.down(columnAmount);
    }

    private Row moveRow(int rowAmount) {
        if (rowAmount > 0) {
            return row.up(rowAmount);
        }
        return row.down(rowAmount);
    }

    private boolean canMoveColumn(int columnAmount) {
        if (columnAmount > 0) {
            return column.canUp(columnAmount);
        }
        return column.canDown(columnAmount);
    }

    private boolean canMoveRow(int rowAmount) {
        if (rowAmount > 0) {
            return row.canUp(rowAmount);
        }
        return row.canDown(rowAmount);
    }

    public Position moveUp() {
        return new Position(column.down(), row);
    }

    public boolean canMoveUp() {
        return column.canDown();
    }

    public Position moveDown() {
        return new Position(column.up(), row);
    }

    public boolean canMoveDown() {
        return column.canUp();
    }

    public Position moveLeft() {
        return new Position(column, row.down());
    }

    public boolean canMoveLeft() {
        return row.canDown();
    }

    public Position moveRight() {
        return new Position(column, row.up());
    }

    public boolean canMoveRight() {
        return row.canUp();
    }

    public Position moveUpRight() {
        return new Position(column.down(), row.up());
    }

    public boolean canMoveUpRight() {
        return column.canDown() && row.canUp();
    }

    public Position moveUpLeft() {
        return new Position(column.down(), row.down());
    }

    public boolean canMoveUpLeft() {
        return column.canDown() && row.canDown();
    }

    public Position moveDownRight() {
        return new Position(column.up(), row.up());
    }

    public boolean canMoveDownRight() {
        return column.canUp() && row.canUp();
    }

    public Position moveDownLeft() {
        return new Position(column.up(), row.down());
    }

    public boolean canMoveDownLeft() {
        return column.canUp() && row.canDown();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return column == position.column && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(column, row);
    }

    @Override
    public String toString() {
        return "Position{" +
            "column=" + column +
            ", row=" + row +
            '}';
    }

    public Position copyOf() {
        return new Position(this.column, this.row);
    }
}
