package direction;

public record Point(int x, int y) {

    public Point minus(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point plus(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point apply(int side) {
        return new Point(x * side, y * side);
    }

    public Point apply(Direction direction) {
        return new Point(x + direction.getDirection().x(), y + direction.getDirection().y());
    }

    public Point apply(Direction direction, int side) {
        Point result = new Point(x, y);
        return result.plus(direction.apply(side));
    }

    public boolean isDestinationDirection(Point destination, Point point) {
        return plus(point).equals(destination);
    }
}
