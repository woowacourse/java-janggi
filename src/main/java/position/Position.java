package position;

import java.util.List;

public record Position(Row row, Column column) {

    public Position(final int row, final int column) {
        this(new Row(row), new Column(column));
    }

    public boolean canMove(final Delta delta) {
        final boolean canMoveRow = row.canMove(delta);
        final boolean canMoveColumn = column.canMove(delta);
        return canMoveRow && canMoveColumn;
    }

    public Position move(final Delta delta) {
        if (!canMove(delta)) {
            throw new IllegalArgumentException("해당 위치로 이동할 수 없습니다.");
        }
        return new Position(row.add(delta), column.move(delta));
    }

    public boolean isSameRow(final Position departure) {
        return row.equals(departure.row);
    }

    public boolean isSameColumn(final Position departure) {
        return column.equals(departure.column);
    }

    public boolean isGapBiggerThanOneStep(final Position destination) {
        return row.isGapBiggerThanOne(destination.row) ||
            column.isGapBiggerThanOne(destination.column);
    }

    public boolean isRowInRange(final Row min, final Row max) {
        return row.isInRange(min, max);
    }

    public boolean isColumnInRange(final Column min, final Column max) {
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

    public Delta calculateDeltaTo(final Position destination) {
        final Delta rowDelta = destination.row.calculateDelta(row);
        final Delta columnDelta = destination.column.calculateDelta(column);
        return rowDelta.add(columnDelta);
    }

    public int getRowIndex() {
        return row.index();
    }

    public int getColumnIndex() {
        return column.index();
    }
}
