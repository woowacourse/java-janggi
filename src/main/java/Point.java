import java.util.Objects;

public class Point {
    private static final int X_MAX = 8;
    private static final int Y_MAX = 9;

    private final int x;
    private final int y;

    public Point(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        if (x < 0 || x > X_MAX) {
            throw new IllegalArgumentException("");
        }
        if (y < 0 || y > Y_MAX) {
            throw new IllegalArgumentException("");
        }
    }

    public boolean isHorizontal(Point opposite) {
        return (this.x == opposite.x || this.y == opposite.y);
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
