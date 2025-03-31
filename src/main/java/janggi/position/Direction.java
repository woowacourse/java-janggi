package janggi.position;

import java.util.List;

public enum Direction {
    UPPER(0, 1),
    UPPER_RIGHT(1, 1),
    RIGHT(1, 0),
    UNDER_RIGHT(1, -1),
    UNDER(0, -1),
    UNDER_LEFT(-1, -1),
    LEFT(-1, 0),
    UPPER_LEFT(-1, 1),
    NONE(0, 0),
    ;

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isDiagonal() {
        return this == UPPER_RIGHT || this == UNDER_RIGHT || this == UNDER_LEFT || this == UPPER_LEFT;
    }

    public static List<Direction> getAllDirection() {
        return List.of(UPPER, UPPER_RIGHT, RIGHT, UNDER_RIGHT, UNDER, UNDER_LEFT, LEFT, UPPER_LEFT);
    }

    public static List<Direction> getDiagonal() {
        return List.of(UPPER_RIGHT, UPPER_LEFT, UNDER_LEFT, UNDER_RIGHT);
    }

    public List<Direction> getNextWithDiagonal() {
        if (this == NONE) {
            return List.of(UPPER, UNDER, LEFT, RIGHT);
        }
        if (this == UPPER) {
            return List.of(UPPER_LEFT, UPPER_RIGHT);
        }
        if (this == UNDER) {
            return List.of(UNDER_LEFT, UNDER_RIGHT);
        }
        if (this == LEFT) {
            return List.of(UPPER_LEFT, UNDER_LEFT);
        }
        if (this == RIGHT) {
            return List.of(UPPER_RIGHT, UNDER_RIGHT);
        }
        return List.of(this);
    }

    public static List<Direction> getFrontDirection() {
        return List.of(Direction.UPPER, Direction.LEFT, Direction.RIGHT, UPPER_LEFT, UPPER_RIGHT);
    }

    public static List<Direction> getBackDirection() {
        return List.of(Direction.UNDER, Direction.LEFT, Direction.RIGHT, UNDER_LEFT, UNDER_RIGHT);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
