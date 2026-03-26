package domain.game;

import domain.vo.Col;
import domain.vo.Row;

public record Position(Col col, Row row) {
    public boolean canShift(int colDelta, int rowDelta) {
        return col.canShift(colDelta) && row.canShift(rowDelta);
    }

    public Position shift(int colDelta, int rowDelta) {
        return new Position(
                col.shift(colDelta),
                row.shift(rowDelta)
        );
    }
}
