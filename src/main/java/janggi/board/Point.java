package janggi.board;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isHorizontallyAlignedWith(Point other) {
        return this.y == other.y;
    }

    public boolean isVerticallyAlignedWith(Point other) {
        return this.x == other.x;
    }

    public int xDistanceTo(Point other) {
        return Math.abs(this.x - other.x);
    }

    public int yDistanceTo(Point other) {
        return Math.abs(this.y - other.y);
    }

    public boolean isXBetween(int startX, int endX) {
        return isInHalfOpenRange(this.x, startX, endX);
    }

    public boolean isYBetween(int startY, int endY) {
        return isInHalfOpenRange(this.y, startY, endY);
    }

    private boolean isInHalfOpenRange(int value, int startRange, int endRange) {
        return startRange <= value && value < endRange;
    }

    public boolean isYGreaterThan(Point other) {
        return this.y > other.y;
    }

    public Set<Point> findHorizontalPointsBetween(Point to) {
        Set<Point> horizontalPoints = new HashSet<>();
        Point next = this.nextHorizontalPointTo(to);

        while (!next.equals(to)) {
            horizontalPoints.add(next);
            next = next.nextHorizontalPointTo(to);
        }

        return horizontalPoints;
    }

    public Set<Point> findVerticalPointsBetween(Point to) {
        Set<Point> verticalPoints = new HashSet<>();
        Point next = this.nextVerticalPointTo(to);

        while (!next.equals(to)) {
            verticalPoints.add(next);
            next = next.nextVerticalPointTo(to);
        }

        return verticalPoints;
    }

    public Point nextHorizontalPointTo(Point to) {
        if (this.x == to.x) {
            throw new IllegalArgumentException("x좌표가 같습니다.");
        }
        if (this.x < to.x) {
            return new Point(this.x + 1, this.y);
        }
        return new Point(this.x - 1, this.y);
    }

    public Point nextVerticalPointTo(Point to) {
        if (this.y == to.y) {
            throw new IllegalArgumentException("y좌표가 같습니다.");
        }
        if (this.y < to.y) {
            return new Point(this.x, this.y + 1);
        }
        return new Point(this.x, this.y - 1);
    }

    public Point midPointBetween(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
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
