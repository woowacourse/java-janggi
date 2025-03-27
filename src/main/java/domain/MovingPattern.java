package domain;

public enum MovingPattern {
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1),
    UP(-1, 0),

    DIAGONAL_UP_RIGHT(-1, 1),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_DOWN_LEFT(1, -1),
    DIAGONAL_UP_LEFT(-1, -1);

    private int x;
    private int y;

    MovingPattern(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isDiagonalPattern() {
        return this == DIAGONAL_DOWN_LEFT ||
                this == DIAGONAL_UP_LEFT ||
                this == DIAGONAL_DOWN_RIGHT ||
                this == DIAGONAL_UP_RIGHT;
    }
}
