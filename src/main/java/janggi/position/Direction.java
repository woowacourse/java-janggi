package janggi.position;

import java.util.List;

public record Direction(int x, int y) {
    public static final Direction UP = new Direction(0, -1);
    public static final Direction DOWN = new Direction(0, 1);
    public static final Direction LEFT = new Direction(-1, 0);
    public static final Direction RIGHT = new Direction(1, 0);

    public static final Direction LEFT_UP = new Direction(-1, -1);
    public static final Direction LEFT_DOWN = new Direction(-1, 1);
    public static final Direction RIGHT_UP = new Direction(1, -1);
    public static final Direction RIGHT_DOWN = new Direction(1, 1);

    public static List<Direction> getCardinalDirections() {
        return List.of(UP, DOWN, LEFT, RIGHT);
    }

    public static List<Direction> getDiagonalDirections() {
        return List.of(LEFT_UP, LEFT_DOWN, RIGHT_UP, RIGHT_DOWN);
    }

    public static Direction calculateDirection(final Position start, final Position end) {
        if (isDiagonalDirection(start, end)) {
            return calculateDiagonalDirection(start, end);
        }
        if (isCardinalDirection(start, end)) {
            return calculateCardinalDirection(start, end);
        }
        throw new IllegalArgumentException("대각선 또는 직선 방향이 아닙니다.");
    }

    private static boolean isDiagonalDirection(final Position start, final Position end) {
        return start.calculateAbsoluteDifferenceX(end) == start.calculateAbsoluteDifferenceY(end);
    }

    private static Direction calculateDiagonalDirection(final Position start, final Position end) {
        int x = getSign(start.calculateDifferenceX(end));
        int y = getSign(start.calculateDifferenceY(end));
        return new Direction(x, y);
    }

    private static boolean isCardinalDirection(final Position start, final Position end) {
        boolean isMoveHorizontal = (start.calculateAbsoluteDifferenceX(end) != 0);
        boolean isMoveVertical = (start.calculateAbsoluteDifferenceY(end) != 0);
        return isMoveHorizontal ^ isMoveVertical;
    }

    private static Direction calculateCardinalDirection(final Position start, final Position end) {
        final int x = start.calculateDifferenceX(end);
        final int y = start.calculateDifferenceY(end);
        if (x == 0) {
            return new Direction(0, getSign(y));
        }
        // NOTE: y == 0
        return new Direction(getSign(x), 0);
    }

    private static int getSign(int value) {
        if (value > 0) {
            return 1;
        }
        return -1;
    }

    public boolean isReverseFrontVerticalDirection(Direction direction) {
        int reverseVerticalDirection = -y;
        return direction.y == reverseVerticalDirection;
    }

    public Direction reverse() {
        return new Direction(-x, -y);
    }
}
