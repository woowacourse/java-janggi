package domain.board;

import domain.movements.Direction;
import execptions.JanggiArgumentException;

public record BoardPoint(int row, int column) {

    private static final int VALID_ROW_SIZE = 10;
    private static final int VALID_COLUMN_SIZE = 9;

    public BoardPoint {
        if (row >= VALID_ROW_SIZE || row < 0 || column >= VALID_COLUMN_SIZE || column < 0) {
            throw new JanggiArgumentException("유효하지 않은 범위입니다.");
        }
    }

    public BoardPoint move(final Direction direction) {
        return new BoardPoint(row + direction.getRow(), column + direction.getColumn());
    }

    public Point toPoint() {
        return new Point(row, column);
    }
}
