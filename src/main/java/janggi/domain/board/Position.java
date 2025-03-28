package janggi.domain.board;

import janggi.domain.move.Direction;

import java.util.*;

public class Position {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;

    private final int x;
    private final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(final Direction direction) {
        return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    public boolean isInBoardRange() {
        return x >= 0 && y >= 0 && x <= X_LIMIT - 1 && y <= Y_LIMIT - 1;
    }

    public boolean isInPalace() {
        return PalacePoint.PALACE_POSITIONS.contains(this);
    }

    public boolean isDiagonalMovable() {
        return PalacePoint.DIAGONAL_MOVABLE_POSITIONS.contains(this);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
