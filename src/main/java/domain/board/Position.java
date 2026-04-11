package domain.board;

import domain.movement.Delta;

public record Position(Column column, Row row) {
    public boolean canShift(Delta delta) {
        return column.canShift(delta.columnDelta())
                && row.canShift(delta.rowDelta());
    }

    public Position shift(Delta delta) {
        return new Position(
                column.shift(delta.columnDelta()),
                row.shift(delta.rowDelta())
        );
    }
}
