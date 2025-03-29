package direction;

public record Point(int x, int y) {

    public Point minus(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point plus(Direction direction) {
        return new Point(x + direction.getX(), y + direction.getY());
    }

    public Point apply(Direction direction) {
        return new Point(x + direction.getX(), y + direction.getY());
    }
}
