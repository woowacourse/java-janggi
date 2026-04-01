package domain.move.directions;

public enum Vector {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),

    LEFT_UP(-1, -1),
    LEFT_DOWN(1, -1),
    RIGHT_UP(-1, 1),
    RIGHT_DOWN(1, 1),
    ;

    private final int dy;
    private final int dx;

    Vector(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }

    public int dy() {
        return dy;
    }

    public int dx() {
        return dx;
    }

}
