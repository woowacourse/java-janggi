package janggi.board;

import java.util.Objects;

public class Position {

    private final int x;
    private final int y;
    private final boolean isPalace;

    public Position(final int x, final int y) {
        this.x = x;
        this.y = y;
        this.isPalace = filterPalace();
    }

    public Position move(Direction direction) {
        return new Position(x + direction.getDx(), y + direction.getDy());
    }

    public boolean isOutOfRange(final int xLimit, final int yLimit) {
        return x < 0 || y < 0 || x > xLimit - 1 || y > yLimit - 1;
    }

    public boolean isPalace() {
        return isPalace;
    }

    public boolean isNotPalace() {
        return !isPalace;
    }

    private boolean filterPalace() {
        return x >= 3 && x <= 5 && !(y >= 3 && y <= 6);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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
}
