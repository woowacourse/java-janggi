package domain.pattern;

public enum Pattern {
    MOVE_RIGHT(0, 1),
    MOVE_DOWN(1, 0),
    MOVE_LEFT(0, -1),
    MOVE_UP(-1, 0),

    MOVE_DIAGONAL_UP_RIGHT(-1, 1),
    MOVE_DIAGONAL_DOWN_RIGHT(1, 1),
    MOVE_DIAGONAL_DOWN_LEFT(1, -1),
    MOVE_DIAGONAL_UP_LEFT(-1, -1);

    private int x;
    private int y;

    Pattern(int x, int y) {
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
