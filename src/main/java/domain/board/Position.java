package domain.board;

import domain.movement.Delta;

public record Position(Column column, Row row) {
    public boolean canShift(Delta delta) {
        return column.canShift(delta.columnDelta().value()) && row.canShift(delta.rowDelta().value());
    }

    public Position shift(Delta delta) {
        return new Position(
                column.shift(delta.columnDelta().value()),
                row.shift(delta.rowDelta().value())
        );
    }
}
