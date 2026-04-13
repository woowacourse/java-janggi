package domain.board;

import domain.movement.vo.Delta;

public record Position(Column column, Row row) {
    public boolean canShift(Delta delta) {
        return column.canShift(delta.columnDelta())
                && row.canShift(delta.rowDelta());
    }

    public boolean isBetween(Position start, Position end) {
        return isColumnBetween(start, end) && isRowBetween(start, end);
    }

    private boolean isColumnBetween(Position start, Position end) {
        return isBetween(column, start.column(), end.column());
    }

    private boolean isRowBetween(Position start, Position end) {
        return isBetween(row, start.row(), end.row());
    }

    private <T extends Comparable<T>> boolean isBetween(T target, T start, T end) {
        return start.compareTo(target) <= 0 && target.compareTo(end) <= 0;
    }

    public Position shift(Delta delta) {
        return new Position(
                column.shift(delta.columnDelta()),
                row.shift(delta.rowDelta())
        );
    }
}
