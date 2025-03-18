package domain.piece;

import java.io.PipedReader;

public enum Move {

    FRONT(-1, 0),
    BACK(1, 0),
    RIGHT(0, 1),
    LEFT(0, -1),
    FRONT_RIGHT(-1, 1),
    FRONT_LEFT(-1, -1),
    BACK_RIGHT(1, 1),
    BACK_LEFT(1, -1)
    ;

    private final int dy;
    private final int dx;

    Move(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public int getDx() {
        return dx;
    }
}
