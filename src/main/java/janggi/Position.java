package janggi;

public record Position(int x, int y) {

    private static final int BOARD_X_LOWER_BOUND = 1;
    private static final int BOARD_X_UPPER_BOUND = 9;
    private static final int BOARD_Y_LOWER_BOUND = 1;
    private static final int BOARD_Y_UPPER_BOUND = 10;

    public Position {
        validateBoardBound(x, y);
    }

    private void validateBoardBound(final int x, final int y) {
        if (x < BOARD_X_LOWER_BOUND || x > BOARD_X_UPPER_BOUND || y < BOARD_Y_LOWER_BOUND || y > BOARD_Y_UPPER_BOUND) {
            throw new IllegalArgumentException("장기판 밖으로는 이동할 수 없습니다.");
        }
    }
}
