package domain.position;

import java.util.Objects;

public class Position {
    public static final int X_MAX = 8;
    public static final int Y_MAX = 9;

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    public static Position from(Point point) {
        return new Position(point.getX(), point.getY());
    }

    public static boolean isCanBePosition(int x, int y) {
        if (x < 0 || x > X_MAX) {
            return false;
        }
        return !(y < 0 || y > Y_MAX);
    }

    private void validate(int x, int y) {
        if (x < 0 || x > X_MAX) {
            throw new IllegalArgumentException("");
        }
        if (y < 0 || y > Y_MAX) {
            throw new IllegalArgumentException("");
        }
    }

    public boolean isStraightLine(Position opposite) {
        return (this.x == opposite.x || this.y == opposite.y);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
