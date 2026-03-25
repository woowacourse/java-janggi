package domain.point;

import java.util.Objects;

public class Point {

    private final int y;
    private final int x;

    public Point(int y, int x) {
        validate(y, x);
        this.y = y;
        this.x = x;
    }

    private void validate(int y, int x) {
        if (0 <= y && y <= 9 && 0 <= x && x <= 8) {
            return;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Point point = (Point) o;
        return y == point.y && x == point.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

}
