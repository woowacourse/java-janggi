package janggi.board;

import java.util.List;

public enum Direction {

    LEFT(-1, 0),
    RIGHT(1, 0),
    UP(0, -1),
    DOWN(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(1, 1),
    ;

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public static List<Direction> straightValues() {
        return List.of(LEFT, RIGHT, UP, DOWN);
    }

    public static List<Direction> diagonalValues() {
        return List.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

}
