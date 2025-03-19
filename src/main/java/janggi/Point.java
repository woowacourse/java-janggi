package janggi;

import java.util.Objects;

public final class Point {

    private final int x;
    private final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String value) {
        if (value.length() != 2) {
            throw new IllegalArgumentException("잘못된 좌표 입력입니다.");
        }
        try {
            String[] split = value.split("", -1);
            this.x = Integer.parseInt(split[0]);
            this.y = Integer.parseInt(split[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
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
