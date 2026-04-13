package domain.board;

import dto.Distance;

public record Position(
        int x,
        int y
) {
    public static final int INITIAL_POSITION = 0;
    public static final int X_MAXIMUM_POSITION = 8;
    public static final int Y_MAXIMUM_POSITION = 9;

    private static final String INVALID_POSITION_RANGE = "[ERROR] x좌표와 y좌표의 범위가 올바르지 않습니다.";
    private static final String INVALID_POSITIONS = "[ERROR] 움직일 기물의 좌표와 움직이고 싶은 좌표는 달라야 합니다.";

    public Position {
        validateRange(x, y);
    }

    private void validateRange(int x, int y) {
        if (isXInvalidRange(x) || isYInvalidRange(y)) {
            throw new IllegalArgumentException(INVALID_POSITION_RANGE);
        }
    }

    private boolean isXInvalidRange(int x) {
        return x < INITIAL_POSITION || x > X_MAXIMUM_POSITION;
    }

    private boolean isYInvalidRange(int y) {
        return y < INITIAL_POSITION || y > Y_MAXIMUM_POSITION;
    }

    public void validatePositions(Position to) {
        if (this.equals(to)) {
            throw new IllegalArgumentException(INVALID_POSITIONS);
        }
    }

    public Distance calculateDistance(Position to) {
        return new Distance(to.x - this.x, to.y - this.y);
    }

    public Position nextPosition(Direction direction) {
        return new Position(x + direction.getX(), y + direction.getY());
    }
}
