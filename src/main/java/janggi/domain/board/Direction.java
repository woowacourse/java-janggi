package janggi.domain.board;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    UP_RIGHT_DIAGONAL(-1, 1),
    UP_LEFT_DIAGONAL(-1, -1),
    DOWN_RIGHT_DIAGONAL(1, 1),
    DOWN_LEFT_DIAGONAL(1, -1);

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
}
