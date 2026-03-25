package janggi.domain;

public record Position(int row, int column) {

    private static final int BOARD_MAX_ROW = 9;
    private static final int BOARD_MIN_ROW = 0;
    private static final int BOARD_MAX_COLUMN = 8;
    private static final int BOARD_MIN_COLUMN = 0;

    public Position {
        if ((row < BOARD_MIN_ROW || row > BOARD_MAX_ROW) || (column < BOARD_MIN_COLUMN || column > BOARD_MAX_COLUMN)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d~%d행, %d~%d열 범위를 벗어날 수 없습니다.",
                            BOARD_MIN_ROW, BOARD_MAX_ROW, BOARD_MIN_COLUMN, BOARD_MAX_COLUMN));
        }
    }

    public Position move(int row, int column) {
        return new Position(this.row + row, this.column + column);
    }
}
