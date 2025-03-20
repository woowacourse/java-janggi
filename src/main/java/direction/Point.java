package direction;

public record Point(
        int x,
        int y
) {
    public Point minus(Point other) {
        return new Point(x-other.x, y-other.y);
    }

    public Point plus(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point multiply(int dir) {
        return new Point(x * dir, y * dir);
    }
}
