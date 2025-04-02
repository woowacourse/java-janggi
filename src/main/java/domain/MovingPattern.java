package domain;

import java.util.List;

public enum MovingPattern {
    RIGHT(0, 1),
    DOWN(1, 0),
    LEFT(0, -1),
    UP(-1, 0),

    DIAGONAL_UP_RIGHT(-1, 1),
    DIAGONAL_DOWN_RIGHT(1, 1),
    DIAGONAL_DOWN_LEFT(1, -1),
    DIAGONAL_UP_LEFT(-1, -1);

    private static final List<MovingPattern> DIAGONAL_MOVES = List.of(
            DIAGONAL_UP_LEFT,
            DIAGONAL_DOWN_LEFT,
            DIAGONAL_DOWN_RIGHT,
            DIAGONAL_UP_RIGHT
    );
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
        return DIAGONAL_MOVES.contains(this);
    }
}
