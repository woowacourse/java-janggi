package janggi.domain;

public class Position {

    private static final int BOARD_MAX_ROW = 10;
    private static final int BOARD_MIN_ROW = 1;
    private static final int BOARD_MAX_COLUMN = 9;
    private static final int BOARD_MIN_COLUMN = 1;
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    private void validateBounds(int row, int column) {
        if ((row < BOARD_MIN_ROW || row > BOARD_MAX_ROW) || (column < BOARD_MIN_COLUMN || column > BOARD_MAX_COLUMN)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d~%d행, %d~%d열 범위를 벗어날 수 없습니다.",
                            BOARD_MIN_ROW, BOARD_MAX_ROW, BOARD_MIN_COLUMN, BOARD_MAX_COLUMN));
        }
    }
}
