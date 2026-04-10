package janggi.domain.piece.path;

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

    public static Direction of(int dx, int dy) {
        for (Direction value : values()) {
            if (value.dx == dx && value.dy == dy) {
                return value;
            }
        }
        throw new IllegalStateException("Direction이 존재하지 않습니다. dx:%d, dy:%d".formatted(dx, dy));
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
