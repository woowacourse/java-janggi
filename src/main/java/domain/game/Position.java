package domain.game;

import domain.vo.Col;
import domain.vo.Delta;
import domain.vo.Row;

public record Position(Col col, Row row) {
    public boolean canShift(Delta delta) {
        return col.canShift(delta.columnDelta().value()) && row.canShift(delta.rowDelta().value());
    }

    public Position shift(Delta delta) {
        return new Position(
                col.shift(delta.columnDelta().value()),
                row.shift(delta.rowDelta().value())
        );
    }
}