package janggi.domain.piece.direction;

public enum Direction {

    RIGHT(1, 0),
    LEFT(-1, 0),
    DOWN(0, -1),
    UP(0, 1),
    RIGHT_DOWN(1, -1),
    RIGHT_UP(1, 1),
    LEFT_DOWN(-1, -1),
    LEFT_UP(-1, 1),
    ;

    private final int dx;
    private final int dy;

    Direction(final int dx, final int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int dx() {
        return dx;
    }

    public int dy() {
        return dy;
    }

    public boolean isDiagonal() {
        return this == RIGHT_DOWN || this == RIGHT_UP || this == LEFT_DOWN || this == LEFT_UP;
    }
}
