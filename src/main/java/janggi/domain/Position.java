package janggi.domain;

import java.util.Objects;
import java.util.function.Predicate;

public class Position {

    private static final Predicate<Integer> X_MOVEABLE = x -> x >= 0 && x < 9;
    private static final Predicate<Integer> Y_MOVEABLE = y -> y >= 0 && y < 10;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    public Position moveTo(int x, int y) {
        return new Position(x, y);
    }

    private void validate(int x, int y) {
        if (!X_MOVEABLE.test(x) || !Y_MOVEABLE.test(y)) {
            throw new IllegalArgumentException("이동할 수 없는 좌표입니다.");
        }
    }

    public boolean hasSameX(int x) {
        return this.x == x;
    }

    public boolean hasSameY(int y) {
        return this.y == y;
    }

    public boolean isSameCoordinate(int x, int y) {
        return hasSameX(x) && hasSameY(y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
