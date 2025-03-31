package model.position;

import java.util.Objects;

public class Position {

    private final Column column;
    private final Row row;

    public Position(Column column, Row row) {
        this.column = column;
        this.row = row;
    }

    public Position(int column, int row) {
        this.column = Column.findColumnFrom(column);
        this.row = Row.findRowFrom(row);
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

    public Column getColumn() {
        return column;
    }

    public Row getRow() {
        return row;
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
}
