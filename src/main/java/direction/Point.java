package direction;

import java.util.Objects;

public class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point minus(Point other) {
        return new Point(x - other.x, y - other.y);
    }

    public Point plus(Point other) {
        return new Point(x + other.x, y + other.y);
    }

    public Point multiply(int dir) {
        return new Point(x * dir, y * dir);
    }

    public boolean isDifferentX(Point point) {
        return point.x != this.x;
    }

    public boolean isDifferentY(Point point) {
        return point.y != this.y;
    }

    public int x() {
        return x;
    }

    public int y() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Point point = (Point) object;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
