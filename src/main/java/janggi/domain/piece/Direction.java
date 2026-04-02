package janggi.domain.piece;

public enum Direction {
    NORTH(1, 0),
    SOUTH(-1, 0),
    WEST(0, -1),
    EAST(0, 1),
    NORTH_WEST(1, -1),
    SOUTH_EAST(-1, 1),
    NORTH_EAST(1, 1),
    SOUTH_WEST(-1, -1),
    ;

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

    public boolean isDiagonal() {
        return dx != 0 && dy != 0;
    }

}
