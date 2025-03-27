package domain.board;

import domain.movements.Direction;

public record TempPoint(int row, int column) {
    private static final int VALID_ROW_SIZE = 10;
    private static final int VALID_COLUMN_SIZE = 9;

    public BoardPoint toPoint() {
        return new BoardPoint(row, column);
    }

    public TempPoint move(final Direction direction) {
        return new TempPoint(row + direction.getRow(), column + direction.getColumn());
    }

    public boolean isInRange() {
        return row < VALID_ROW_SIZE && row >= 0 && column < VALID_COLUMN_SIZE && column >= 0;
    }

}
