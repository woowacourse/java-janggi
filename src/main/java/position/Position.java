package position;

import java.util.List;
import pieces.Side;

public record Position(Row row, Column column) {

    public Position(final int row, final int column) {
        this(new Row(row), new Column(column));
    }

    public boolean canMove(Delta delta) {
        boolean canMoveRow = row.canMove(delta);
        boolean canMoveColumn = column.canMove(delta);
        return canMoveRow && canMoveColumn;
    }

    public Position move(Delta delta) {
        return new Position(row.add(delta), column.move(delta));
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

    public boolean isRowInRange(Row min, Row max) {
        return row.isInRange(min, max);
    }

    public boolean isColumnInRange(Column min, Column max) {
        return column.isInRange(min, max);
    }

    public Position reverse() {
        return new Position(row.reverse(), column.reverse());
    }

    public List<Position> getMovableOneStepDiagonals() {
        return Delta.getDiagonals().stream()
            .filter(this::canMove)
            .map(this::move)
            .toList();
    }

    public Delta calucalteDelta(Position destination) {
        Delta rowDelta = destination.row.calculateDelta(row);
        Delta columnDelta = destination.column.calculateDelta(column);
        return rowDelta.add(columnDelta);
    }
}
