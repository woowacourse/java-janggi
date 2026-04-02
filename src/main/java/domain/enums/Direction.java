package domain.enums;

public enum Direction {
    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, 1),
    RIGHT(0, -1),

    UP_LEFT(1, 1),
    UP_RIGHT(1, -1),
    DOWN_LEFT(-1, 1),
    DOWN_RIGHT(-1, -1),
    LEFT_UP(1, 1),
    LEFT_DOWN(-1, 1),
    RIGHT_UP(-1, -1),
    RIGHT_DOWN(1, -1);

    private final int dx;
    private final int dy;

    Direction(int dx, int dy) {
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
