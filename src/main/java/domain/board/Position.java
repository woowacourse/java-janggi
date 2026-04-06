package domain.board;

import exception.ErrorMessage;

public record Position(int x, int y) {
    private static final int MIN_RANGE = 0;
    private static final int MAX_WIDTH_RANGE = 8;
    private static final int MAX_HEIGHT_RANGE = 9;

    public Position {
        validateRange(x, y);
    }

    private void validateRange(int x, int y) {
        if (x < MIN_RANGE || x > MAX_WIDTH_RANGE) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_BOARD.getMessage());
        }

        if (y < MIN_RANGE || y > MAX_HEIGHT_RANGE) {
            throw new IllegalArgumentException(ErrorMessage.OUT_OF_BOARD.getMessage());
        }
    }
}
