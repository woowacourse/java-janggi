package janggi.board;

import janggi.move.Direction;

import java.util.*;

public class Position {

    private static final int X_LIMIT = 9;
    private static final int Y_LIMIT = 10;
    private static final Set<Position> palacePositions = Set.of(
            new Position(3, 0), new Position(5, 0),
            new Position(4, 1),
            new Position(3, 2), new Position(5, 2),

            new Position(3, 9), new Position(5, 9),
            new Position(4, 8),
            new Position(3, 7), new Position(5, 7)
    );

    private final int x;
    private final int y;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Position move(final Direction direction) {
        return new Position(x + direction.getDeltaX(), y + direction.getDeltaY());
    }

    public boolean isOutOfRange() {
        return x < 0 || y < 0 || x > X_LIMIT - 1 || y > Y_LIMIT - 1;
    }

    public boolean isInPalace() {
        return palacePositions.contains(this);
    }

    public boolean isPalaceCorner() {
        List<Position> palaceCornerPositions = new ArrayList<>(
                Arrays.asList(new Position(3, 0), new Position(5, 0),
                        new Position(3, 2), new Position(5, 2),
                        new Position(3, 9), new Position(5, 9),
                        new Position(3, 7), new Position(5, 7))
        );
        return palaceCornerPositions.contains(this);
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
