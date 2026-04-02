package position;

import pieces.Side;

public record Position(Row row, Column column) {

    public Position(int row, int column) {
        this(new Row(row), new Column(column));
    }

    public boolean canMove(Delta delta) {
        boolean canMoveRow = row.canMove(delta);
        boolean canMoveColumn = column.canMove(delta);
        return canMoveRow && canMoveColumn;
    }

    public Position move(Delta delta) {
        return new Position(row.add(delta), column.add(delta));
    }

    public boolean isSameRow(Position departure) {
        return this.row.equals(departure.row);
    }

    public boolean isSameColumn(Position departure) {
        return this.column.equals(departure.column);
    }

    public boolean isBackRow(Position destination, Side side) {
        if (side.isCho()) {
            return this.row.isBelow(destination.row);
        }
        return this.row.isAbove(destination.row);
    }

    public boolean isLeftColumn(Position destination, Side side) {
        if (side.isCho()) {
            return this.column.isLeft(destination.column);
        }
        return this.column.isRight(destination.column);
    }

    public boolean isGapBiggerThanOne(Position destination) {
        return row.isGapBiggerThanOne(destination.row) ||
            column.isGapBiggerThanOne(destination.column);
    }
}
