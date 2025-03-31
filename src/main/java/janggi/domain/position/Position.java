package janggi.domain.position;

import java.util.Objects;
import java.util.function.Predicate;

public final class Position {

    private static final Predicate<Integer> X_MOVEABLE = x -> x >= 0 && x < 9;
    private static final Predicate<Integer> Y_MOVEABLE = y -> y >= 0 && y < 10;
    public static final int PALACE_X_START = 3;
    public static final int PALACE_X_END= 5;
    public static final int PALACE_BOTTOM_Y_START = 0;
    public static final int PALACE_BOTTOM_Y_END = 2;
    public static final int PALACE_TOP_Y_START = 7;
    public static final int PALACE_TOP_Y_END = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    private void validate(int x, int y) {
        if (!X_MOVEABLE.test(x) || !Y_MOVEABLE.test(y)) {
            throw new IllegalArgumentException("이동할 수 없는 좌표입니다.");
        }
    }

    public boolean isPalace() {
        return PALACE_X_START <= x && x <= PALACE_X_END
            && (PALACE_BOTTOM_Y_START <= y && y <= PALACE_BOTTOM_Y_END
            || PALACE_TOP_Y_START <= y && y <= PALACE_TOP_Y_END);
    }

    public boolean isPalaceCorner() {
        return (x == PALACE_X_START|| x == PALACE_X_END)
            && (y == PALACE_BOTTOM_Y_START || y == PALACE_BOTTOM_Y_END
            || y == PALACE_TOP_Y_START || y == PALACE_TOP_Y_END);
    }

    public boolean isDiagnose(Position destination) {
        return getYDistance(destination) == getXDistance(destination);
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
