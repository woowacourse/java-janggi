package domain.position;

import domain.direction.Direction;

public record ChessPosition(
        int row,
        int column
) {
    public static final int MIN_ROW = 0;
    public static final int MAX_ROW = 9;
    public static final int MIN_COL = 0;
    public static final int MAX_COL = 8;

    public ChessPosition {
        if (!isValid(row, column)) {
            throw new IllegalArgumentException(
                    String.format("위치는 (%d, %d) ~ (%d, %d) 값만 가능합니다.", MIN_ROW, MIN_COL, MAX_ROW, MAX_COL)
            );
        }
    }

    private boolean isValid(final int row, final int col) {
        return row >= MIN_ROW && row <= MAX_ROW && col >= MIN_COL && col <= MAX_COL;
    }

    public boolean canMove(Direction direction) {
        return isValid(row + direction.dr, column + direction.dc);
    }

    public ChessPosition move(Direction direction) {
        return new ChessPosition(row + direction.dr, column + direction.dc);
    }
}
