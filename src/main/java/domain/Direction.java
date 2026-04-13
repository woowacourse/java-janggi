package domain;

import static constant.ErrorMessage.INVALID_DIRECTION;

public enum Direction {
    UP(0,-1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Direction of(int dx, int dy) {
        for (Direction direction : values()) {
            if (direction.x == dx && direction.y == dy) {
                return direction;
            }
        }
        throw new IllegalArgumentException(String.format(INVALID_DIRECTION, dx, dy));
    }
}
