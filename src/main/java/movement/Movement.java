package movement;

public enum Movement {

    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(LEFT.x, UP.y),
    RIGHT_UP(RIGHT.x, UP.y),
    LEFT_DOWN(LEFT.x, DOWN.y),
    RIGHT_DOWN(RIGHT.x, DOWN.y)
    ;

    private final int x;

    private final int y;

    Movement(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }
}
