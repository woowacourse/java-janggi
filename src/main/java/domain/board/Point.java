package domain.board;

import domain.movements.Direction;
import execptions.JanggiArgumentException;

public record Point(int row, int column) {

    private static final int VALID_ROW_SIZE = 10;
    private static final int VALID_COLUMN_SIZE = 9;

    public Point {
        if (row >= VALID_ROW_SIZE || row < 0 || column >= VALID_COLUMN_SIZE || column < 0) {
            throw new JanggiArgumentException("유효하지 않은 범위입니다.");
        }
    }

    public Point move(final Direction direction) {
        return new Point(row + direction.getRow(), column + direction.getColumn());
    }

    public TempPoint toTempPoint() {
        return new TempPoint(row, column);
    }
}
