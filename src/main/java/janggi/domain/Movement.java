package janggi.domain;

public enum Movement {
    UP(1, 0),
    DOWN(-1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    UP_RIGHT(1, 1),
    UP_LEFT(1, -1),
    DOWN_RIGHT(-1, 1),
    DOWN_LEFT(-1, -1);

    private final int dx;
    private final int dy;

    Movement(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
