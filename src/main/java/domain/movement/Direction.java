package domain.movement;

import java.util.List;

public enum Direction {

    UPPER(0, 1),
    UPPER_RIGHT(1, 1),
    RIGHT(1, 0),
    LOWER_RIGHT(1, -1),
    LOWER(0, -1),
    LOWER_LEFT(-1, -1),
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

    public static List<Direction> getStraight() {
        return List.of(UPPER, LOWER, LEFT, RIGHT);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
