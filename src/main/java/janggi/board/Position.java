package janggi.board;

import janggi.move.Direction;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(Direction direction) {
        return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    public boolean isOutOfRange(final int xLimit, final int yLimit) {
        return x < 0 || y < 0 || x > xLimit - 1 || y > yLimit - 1;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Position{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
