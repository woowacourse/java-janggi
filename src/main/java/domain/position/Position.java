package domain.position;

import java.util.Objects;

public class Position {

    public static final String INVALID_POSITION_EXCEPTION = "유효하지 않은 장기판 위치입니다.";
    public static final int X_MAX = 8;
    public static final int Y_MAX = 9;

    private final int x;
    private final int y;

    private Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
    }

    public static Position of(int x, int y) {
        return new Position(x, y);
    }

    public static Position from(Point point) {
        return new Position(point.getX(), point.getY());
    }

    private void validate(int x, int y) {
        if (x < 0 || x > X_MAX) {
            throw new IllegalArgumentException(INVALID_POSITION_EXCEPTION);
        }
        if (y < 0 || y > Y_MAX) {
            throw new IllegalArgumentException(INVALID_POSITION_EXCEPTION);
        }
    }

    public static boolean isCanBePosition(Point point) {
        if (point.getX() < 0 || point.getX() > X_MAX) {
            return false;
        }
        return !(point.getY() < 0 || point.getY() > Y_MAX);
    }

    public double calculateDistance(Position other) {
        int xDifference = Math.abs(this.getX() - other.getX());
        int yDifference = Math.abs(this.getY() - other.getY());
        return Math.sqrt(Math.pow(xDifference, 2) + Math.pow(yDifference, 2));
    }

    public boolean isHorizontalOrVertical(Position opposite) {
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
