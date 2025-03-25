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

    public boolean isHorizontallyAlignedWith(Point otherPoint) {
        return this.y == otherPoint.y;
    }

    public boolean isVerticallyAlignedWith(Point otherPoint) {
        return this.x == otherPoint.x;
    }

    public int getXDistanceFrom(Point otherPoint) {
        return Math.abs(this.x - otherPoint.x);
    }

    public int getYDistanceFrom(Point otherPoint) {
        return Math.abs(this.y - otherPoint.y);
    }

    public boolean isXWithin(int minInclusive, int maxExclusive) {
        return isWithinRange(this.x, minInclusive, maxExclusive);
    }

    public boolean isYWithin(int minInclusive, int maxExclusive) {
        return isWithinRange(this.y, minInclusive, maxExclusive);
    }

    private boolean isWithinRange(int value, int minInclusive, int maxExclusive) {
        return minInclusive <= value && value < maxExclusive;
    }

    public boolean isYGreaterThan(Point other) {
        return this.y > other.y;
    }

    public Set<Point> findHorizontalPointsBetween(Point target) {
        Set<Point> horizontalPoints = new HashSet<>();
        Point next = this.getNextHorizontalPointToward(target);

        while (!next.equals(target)) {
            horizontalPoints.add(next);
            next = next.getNextHorizontalPointToward(target);
        }

        return horizontalPoints;
    }

    public Set<Point> findVerticalPointsBetween(Point target) {
        Set<Point> verticalPoints = new HashSet<>();
        Point next = this.getNextVerticalPointToward(target);

        while (!next.equals(target)) {
            verticalPoints.add(next);
            next = next.getNextVerticalPointToward(target);
        }

        return verticalPoints;
    }

    public Point getNextHorizontalPointToward(Point other) {
        if (this.x == other.x) {
            throw new IllegalArgumentException("x좌표가 같습니다.");
        }
        if (this.x < other.x) {
            return new Point(this.x + 1, this.y);
        }
        return new Point(this.x - 1, this.y);
    }

    public Point getNextVerticalPointToward(Point other) {
        if (this.y == other.y) {
            throw new IllegalArgumentException("y좌표가 같습니다.");
        }
        if (this.y < other.y) {
            return new Point(this.x, this.y + 1);
        }
        return new Point(this.x, this.y - 1);
    }

    public Point getCenterPointWith(Point other) {
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
