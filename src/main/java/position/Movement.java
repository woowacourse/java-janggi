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
    UP_RIGHT(1, 1),
    UP_LEFT(-1, 1),
    DOWN_RIGHT(1, -1),
    DOWN_LEFT(-1, -1),

    UP_UP_RIGHT(1, 2),
    UP_RIGHT_RIGHT(2, 1),
    DOWN_RIGHT_RIGHT(2, -1),
    DOWN_DOWN_RIGHT(1, -2),
    DOWN_DOWN_LEFT(-1, -2),
    DOWN_LEFT_LEFT(-2, -1),
    UP_LEFT_LEFT(-2, 1),
    UP_UP_LEFT(-1, 2),

    UP_UP_UP_RIGHT_RIGHT(2, 3),
    UP_UP_RIGHT_RIGHT_RIGHT(3, 2),
    DOWN_DOWN_RIGHT_RIGHT_RIGHT(3, -2),
    DOWN_DOWN_DOWN_RIGHT_RIGHT(2, -3),
    DOWN_DOWN_DOWN_LEFT_LEFT(-2, -3),
    DOWN_DOWN_LEFT_LEFT_LEFT(-3, -2),
    UP_UP_LEFT_LEFT_LEFT(-3, 2),
    UP_UP_UP_LEFT_LEFT(-2, 3);;

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
