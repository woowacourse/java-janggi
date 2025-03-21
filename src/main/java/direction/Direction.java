package direction;

public enum Direction {
    LEFT(new Point(-1, 0)),
    RIGHT(new Point(1, 0)),
    UP(new Point(0, -1)),
    DOWN(new Point(0, 1)),
    UP_LEFT_DIAGONAL(new Point(-1, -1)),
    DOWN_LEFT_DIAGONAL(new Point(-1, 1)),
    UP_RIGHT_DIAGONAL(new Point(1, -1)),
    DOWN_RIGHT_DIAGONAL(new Point(1, 1));

    private final Point direction;

    Direction(Point direction) {
        this.direction = direction;
    }

    public Point getDirection() {
        return direction;
    }

    public Point multiply(int dir) {
        return this.direction.multiply(dir);
    }
}
