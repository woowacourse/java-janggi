package janggi.domain.position;

import java.util.List;

public enum Direction {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_RIGHT(1, 1),
    UP_LEFT(1, -1),
    DOWN_RIGHT(-1, 1),
    DOWN_LEFT(-1, -1);

    private final int dr;
    private final int dc;

    Direction(int dr, int dc) {
        this.dr = dr;
        this.dc = dc;
    }

    public static List<Direction> straight() {
        return List.of(UP, DOWN, LEFT, RIGHT);
    }

    public static List<Direction> diagonal() {
        return List.of(UP_RIGHT, UP_LEFT, DOWN_LEFT, DOWN_RIGHT);
    }

    public static List<Direction> all() {
        return List.of(values());
    }

    public boolean isUp() {
        return dr > 0;
    }

    public boolean isDown() {
        return dr < 0;
    }

    int dr() {
        return dr;
    }

    int dc() {
        return dc;
    }
}
