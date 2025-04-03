package janggi.position;

public record Position(int x, int y) {

    private static final int BOARD_X_LOWER_BOUND = 1;
    private static final int BOARD_X_UPPER_BOUND = 9;
    private static final int BOARD_Y_LOWER_BOUND = 1;
    private static final int BOARD_Y_UPPER_BOUND = 10;

    private static final BoardRoute boardRoute = new BoardRoute(new StandardBoardRouteGenerator());

    public Position {
        validateBoardBound(x, y);
    }

    public static Position from(final String yx) {
        int y = yx.charAt(0) - '0';
        if(y == 0) {
            y = 10;
        }
        int x = yx.charAt(1) - '0';
        return new Position(x, y);
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

    public boolean isInBounds(final Direction direction) {
        try {
            new Position(x + direction.x(), y + direction.y());
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public boolean hasDirection(final Direction direction) {
        return isInBounds(direction) && boardRoute.hasDirection(this, direction);
    }

    public boolean isMoveDistanceOneBlock(final Position end) {
        final int maxMoveDistance = Math.max(calculateAbsoluteDifferenceX(end), calculateAbsoluteDifferenceY(end));
        return maxMoveDistance == 1;
    }
}
