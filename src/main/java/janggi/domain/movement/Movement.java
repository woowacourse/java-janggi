package janggi.domain.movement;

import java.util.List;

public record Movement(
        int x, int y
) {
    public static final Movement UP = new Movement(-1, 0);
    public static final Movement DOWN = new Movement(1, 0);
    public static final Movement LEFT = new Movement(0, -1);
    public static final Movement RIGHT = new Movement(0, 1);
    public static final Movement UP_LEFT = new Movement(-1, -1);
    public static final Movement UP_RIGHT = new Movement(-1, 1);
    public static final Movement DOWN_LEFT = new Movement(1, -1);
    public static final Movement DOWN_RIGHT = new Movement(1, 1);
    public static final List<Movement> UNIT_MOVEMENTS = List.of(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT,
            DOWN_RIGHT);

    public static Movement findUnitMovement(final int x, final int y) {
        if (x < 0 && y < 0) {
            return UP_LEFT;
        }
        if (x < 0 && y > 0) {
            return UP_RIGHT;
        }
        if (x > 0 && y < 0) {
            return DOWN_LEFT;
        }
        if (x > 0 && y > 0) {
            return DOWN_RIGHT;
        }
        return findStraightUnitMovement(x, y);
    }

    public static Movement findStraightUnitMovement(final int x, final int y) {
        if (x < 0 && y == 0) {
            return UP;
        }
        if (x > 0 && y == 0) {
            return DOWN;
        }
        if (x == 0 && y < 0) {
            return LEFT;
        }
        if (x == 0 && y > 0) {
            return RIGHT;
        }
        throw new IllegalStateException("원래 위치로 이동할 수 없거나, 방향을 특정할 수 없습니다.");
    }

    public static boolean isUnitMovement(final Movement value) {
        return Movement.UNIT_MOVEMENTS.contains(value);
    }

    public Movement plus(final Movement other) {
        return new Movement(x + other.x, y + other.y());
    }
}