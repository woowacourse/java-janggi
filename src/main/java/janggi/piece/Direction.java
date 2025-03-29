package janggi.piece;

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

    public static List<Direction> getStraight(Team team) {
        if (team == Team.CHO) {
            return List.of(Direction.UNDER, Direction.LEFT, Direction.RIGHT);
        }
        return List.of(Direction.UPPER, Direction.LEFT, Direction.RIGHT);
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

    public int moveColumn(int x) {
        return this.x + x;
    }

    public int moveRow(int y) {
        return this.y + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
