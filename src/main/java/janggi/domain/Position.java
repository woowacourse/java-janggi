package janggi.domain;

import java.util.Objects;
import java.util.function.Predicate;

public final class Position implements Cloneable {

    private static final Predicate<Integer> X_MOVEABLE = x -> x >= 0 && x < 9;
    private static final Predicate<Integer> Y_MOVEABLE = y -> y >= 0 && y < 10;

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

    @Override
    protected Position clone() throws CloneNotSupportedException {
        return (Position) super.clone();
    }
}
