package janggi.domain.piece;

public record Position(int x, int y) {

    private static final int MIN_POSITION = 0;
    private static final int MAX_X_POSITION = 8;
    private static final int MAX_Y_POSITION = 9;

    public Position {
        validatePosition(x, y);
    }

    private void validatePosition(final int x, final int y) {
        if (isInvalidPosition(x, y)) {
            throw new IllegalArgumentException("보드를 벗어났습니다.");
        }
    }

    private boolean isInvalidPosition(final int x, final int y) {
        return x < MIN_POSITION || x > MAX_X_POSITION || y < MIN_POSITION || y > MAX_Y_POSITION;
    }
}
