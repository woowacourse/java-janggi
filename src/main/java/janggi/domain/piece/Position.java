package janggi.domain.piece;

import java.util.Objects;

public final class Position {

    private static final int X_MIN_VALUE = 0;
    private static final int X_MAX_VALUE = 8;
    private static final int Y_MIN_VALUE = 0;
    private static final int Y_MAX_VALUE = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        if (x < X_MIN_VALUE || x >= X_MAX_VALUE) {
            throw new IllegalArgumentException("x의 범위가 잘못되었습니다.");
        }
        if (y < Y_MIN_VALUE || y >= Y_MAX_VALUE) {
            throw new IllegalArgumentException("y의 범위가 잘못되었습니다.");
        }
    }

    public boolean hasSameX(Position other) {
        return this.x == other.x;
    }

    public boolean hasSameY(Position other) {
        return this.y == other.y;
    }

    public int getXDistance(Position destination) {
        return Math.abs(destination.x - x);
    }

    public int getYDistance(Position destination) {
        return Math.abs(destination.y - y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Position{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Position position)) {
            return false;
        }
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
