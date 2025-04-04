package janggi.domain.board;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class Point {

    private static final int COORDINATES_COUNT = 2;
    public static final int X_COORDINATE_INDEX = 0;
    public static final int Y_COORDINATE_INDEX = 1;

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String value) {
        if (value.length() != COORDINATES_COUNT) {
            throw new IllegalArgumentException("잘못된 좌표 입력입니다.");
        }
        try {
            this.x = Integer.parseInt(String.valueOf(value.charAt(X_COORDINATE_INDEX)));
            this.y = Integer.parseInt(String.valueOf(value.charAt(Y_COORDINATE_INDEX)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    public boolean isHorizontallyAlignedWith(Point other) {
        return this.y == other.y;
    }

    public boolean isVerticallyAlignedWith(Point other) {
        return this.x == other.x;
    }

    public boolean isDiagonallyAlignedWith(Point other) {
        return xDistanceTo(other) == yDistanceTo(other);
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

    public Set<Point> findDiagonalPointsBetween(Point to) {
        Set<Point> diagonalPoints = new HashSet<>();
        Point next = this.nextDiagonalPointTo(to);

        while (!next.equals(to)) {
            diagonalPoints.add(next);
            next = next.nextDiagonalPointTo(to);
        }

        return diagonalPoints;
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

    public Point nextDiagonalPointTo(Point to) {
        if (this.x == to.x || this.y == to.y) {
            throw new IllegalArgumentException("두 지점의 좌표가 같습니다.");
        }
        if ((this.x < to.x) && (this.y < to.y)) {
            return new Point(this.x + 1, this.y + 1);
        }
        if ((this.x > to.x) && (this.y < to.y)) {
            return new Point(this.x - 1, this.y + 1);
        }
        if ((this.x < to.x) && (this.y > to.y)) {
            return new Point(this.x + 1, this.y - 1);
        }
        return new Point(this.x - 1, this.y - 1);
    }

    public Point midPointBetween(Point other) {
        return new Point((this.x + other.x) / 2, (this.y + other.y) / 2);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
