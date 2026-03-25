package domain;

public class Position {
    private static final int INITIAL_POSITION = 0;
    private static final int X_MAXIMUM_POSITION = 8;
    private static final int Y_MAXIMUM_POSITION = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validateRange(x, y);
        this.x = x;
        this.y = y;
    }

    private void validateRange(int x, int y) {
        if (isXInvalidRange(x) || isYInvalidRange(y)) {
            throw new IllegalArgumentException("[ERROR] x좌표와 y좌표의 범위가 올바르지 않습니다.");
        }
    }

    private boolean isXInvalidRange(int x) {
        return x < INITIAL_POSITION || x > X_MAXIMUM_POSITION;
    }

    private boolean isYInvalidRange(int y) {
        return y < INITIAL_POSITION || y > Y_MAXIMUM_POSITION;
    }
}
