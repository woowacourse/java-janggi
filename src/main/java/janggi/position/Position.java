package janggi.position;

import java.util.Objects;

public class Position {
    private final PositionX x;
    private final PositionY y;

    public Position(int x, int y) {
        this.x = new PositionX(x);
        this.y = new PositionY(y);
    }

    public Position(PositionX x, PositionY y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x.getValue();
    }

    public int getY() {
        return y.getValue();
    }

    public Position applyOffsetToPosition(int offsetX, int offsetY) {
        return new Position(x.plus(offsetX), y.plus(offsetY));
    }

    public Position updatePosition(int x, int y) {
        return new Position(x, y);
    }

    public Position updatePosition(PositionX x, PositionY y) {
        return new Position(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Objects.equals(x, position.x) && Objects.equals(y, position.y);
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
