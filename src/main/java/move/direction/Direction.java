package move.direction;

public enum Direction {

    UP(1, 0),
    DOWN(-1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(1, -1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(-1, 1);

    private final int y;
    private final int x;

    Direction(int y, int x) {
        this.y = y;
        this.x = x;
    }

    public int y() {
        return y;
    }

    public int x() {
        return x;
    }
}
