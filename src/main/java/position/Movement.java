package position;

public enum Movement {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    LEFT_UP(-1, 1),
    RIGHT_UP(1, 1),
    LEFT_DOWN(-1, -1),
    RIGHT_DOWN(1, -1),
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
