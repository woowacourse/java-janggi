package janggi.position;

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

    public Position offset(int offsetX, int offsetY) {
        return new Position(x + offsetX, y + offsetY);
    }

    public Position move(final Direction direction) {
        return new Position(x + direction.x(), y + direction.y());
    }

    public Direction calculateDirection(final Position end) {
        int differenceX = calculateDifferenceX(end);
        int differenceY = calculateDifferenceY(end);
        if (differenceX > 0) {
            return Direction.RIGHT;
        }
        if (differenceX < 0) {
            return Direction.LEFT;
        }
        if (differenceY < 0) {
            return Direction.UP;
        }
        return Direction.DOWN;
    }

    public int calculateAbsoluteDifferenceX(final Position end) {
        return Math.abs(end.x() - x);
    }

    public int calculateAbsoluteDifferenceY(final Position end) {
        return Math.abs(end.y() - y);
    }

    public int calculateDifferenceX(final Position end) {
        return end.x() - x;
    }

    public int calculateDifferenceY(final Position end) {
        return end.y() - y;
    }
}
