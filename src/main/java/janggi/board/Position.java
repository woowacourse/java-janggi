package janggi.board;

public record Position(int x, int y) {
    private static final String INVALID_RANGE = "해당 좌표가 장기 판 범위를 벗어납니다";

    private static final int BOARD_AXIS_MIN = 0;
    private static final int BOARD_X_AXIS_MAX = 8;
    private static final int BOARD_Y_AXIS_MAX = 9;

    public Position {
        validateRange(x, y);
    }

    private void validateRange(int x, int y) {
        if (x < BOARD_AXIS_MIN || x > BOARD_X_AXIS_MAX || y < BOARD_AXIS_MIN || y > BOARD_Y_AXIS_MAX) {
            throw new IllegalArgumentException(INVALID_RANGE);
        }
    }

    public int distanceX(Position other) {
        return Math.abs(this.x - other.x);
    }

    public int distanceY(Position other) {
        return Math.abs(this.y - other.y);
    }
}
