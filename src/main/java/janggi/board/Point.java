package janggi.board;

import java.util.Objects;

public final class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHorizontal(Point otherPoint) {
        return this.y == otherPoint.y;
    }

    public boolean isVertical(Point otherPoint) {
        return this.x == otherPoint.x;
    }

    public int calculateXDistance(Point otherPoint) {
        return Math.abs(this.x - otherPoint.x);
    }

    public int calculateYDistance(Point otherPoint) {
        return Math.abs(this.y - otherPoint.y);
    }

    public boolean isXInRange(int start, int end) {
        return isWithinRange(this.x, start, end);
    }

    public boolean isYInRange(int start, int end) {
        return isWithinRange(this.y, start, end);
    }

    private boolean isWithinRange(int coordinate, int start, int end) {
        return start <= coordinate && coordinate < end;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
