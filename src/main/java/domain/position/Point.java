package domain.position;

import java.util.Objects;

public final class Point {
    public static final Double DIAGONAL_UNIT = Math.sqrt(2);
    private static final int MAX_X = 8;
    private static final int MAX_Y = 9;
    private final int x;
    private final int y;

    private Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(final String x, final String y) {
        final int parsedX = parseInt(x);
        final int parsedY = parseInt(y);
        validateRange(parsedX, MAX_X);
        validateRange(parsedY, MAX_Y);
        return new Point(parsedX, parsedY);
    }

    public static Point newInstance(final int x, final int y) {
        return new Point(x, y);
    }

    private static void validateRange(final int point, final int maxPoint) {
        if (point < 0 || point > maxPoint) {
            throw new IllegalArgumentException("X축은 0부터 8까지, Y축은 0부터 9까지만 가능합니다.");
        }
    }

    private static int parseInt(final String x) {
        try {
            return Integer.parseInt(x);
        } catch (final NumberFormatException e) {
            throw new IllegalArgumentException("좌표값은 숫자만 가능합니다.");
        }
    }

    public boolean isPalace() {
        return isGreenPalace() || isRedPalace();
    }

    public boolean isGreenPalace() {
        return x >= 3 && x <= 5 && y >= 0 && y <= 2;
    }

    public boolean isRedPalace() {
        return x >= 3 && x <= 5 && y >= 7 && y <= 9;
    }

    public int distanceToMaxX() {
        return MAX_X - x;
    }

    public int distanceToMinX() {
        return x;
    }

    public int distanceToMaxY() {
        return MAX_Y - y;
    }

    public int distanceToMinY() {
        return y;
    }

    public Direction generateDirection(final Point other) {
        final int pointX = calculateSubtractionX(other);
        final int pointY = calculateSubtractionY(other);
        return new Direction(pointX, pointY);
    }

    public int calculateSubtractionX(final Point other) {
        return other.x - this.x;
    }

    public int calculateSubtractionY(final Point other) {
        return other.y - this.y;
    }

    public Point up() {
        return new Point(x, y + 1);
    }

    public Point down() {
        return new Point(x, y - 1);
    }

    public Point left() {
        return new Point(x - 1, y);
    }

    public Point right() {
        return new Point(x + 1, y);
    }

    public Point rightUp() {
        return new Point(x + 1, y + 1);
    }

    public Point rightDown() {
        return new Point(x + 1, y - 1);
    }

    public Point leftUp() {
        return new Point(x - 1, y + 1);
    }

    public Point leftDown() {
        return new Point(x - 1, y - 1);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
