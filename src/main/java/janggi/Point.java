package janggi;

import java.util.Objects;

public final class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHorizontal(Point otherPoint) {
        return this.x == otherPoint.x;
    }

    public boolean isVertical(Point otherPoint) {
        return this.y == otherPoint.y;
    }

    @Override
    public boolean equals(Object otherPoint) {
        if (otherPoint == null || getClass() != otherPoint.getClass()) {
            return false;
        }
        Point point = (Point) otherPoint;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
