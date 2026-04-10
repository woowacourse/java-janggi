package janggi.domain.board;

public record Position(int row, int column) {

    public static final int BOARD_MAX_COLUMN = 8;
    public static final int BOARD_MIN_COLUMN = 0;
    private static final int BOARD_MAX_ROW = 9;
    private static final int BOARD_MIN_ROW = 0;

    public Position {
        if (!isValidate(row, column)) {
            throw new IllegalArgumentException(
                    String.format("[ERROR] %d~%d행, %d~%d열 범위를 벗어날 수 없습니다.",
                            BOARD_MIN_ROW, BOARD_MAX_ROW, BOARD_MIN_COLUMN, BOARD_MAX_COLUMN));
        }
    }

    public boolean canMove(int row, int column) {
        return isValidate(this.row + row, this.column + column);
    }

    private boolean isValidate(int row, int column) {
        return (BOARD_MIN_ROW <= row && row <= BOARD_MAX_ROW) && (BOARD_MIN_COLUMN <= column
                && column <= BOARD_MAX_COLUMN);
    }

    public Position move(int row, int column) {
        return new Position(this.row + row, this.column + column);
    }

    public Position moduloRow(int divisor) {
        return new Position(this.row % divisor, this.column);
    }
}
