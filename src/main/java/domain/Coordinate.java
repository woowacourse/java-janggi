package domain;

import java.util.Objects;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        validateXCoordinate(x);
        validateYCoordinate(y);
        this.x = x;
        this.y = y;
    }

    public boolean canMove(Movement movement) {
        int newX = this.x + movement.deltaX();
        int newY = this.y + movement.deltaY();

        return !isInvalidX(newX) && !isInvalidY(newY);
    }

    public Coordinate move(Movement movement) {
        int newX = this.x + movement.deltaX();
        int newY = this.y + movement.deltaY();

        return new Coordinate(newX, newY);
    }

    private boolean isInvalidX(int x) {
        return x < 1 || x > 9;
    }

    private void validateXCoordinate(int x) {
        if (isInvalidX(x)) {
            throw new IllegalArgumentException("가로 좌표는 1에서 9사이여야 합니다.");
        }
    }

    private boolean isInvalidY(int y) {
        return y < 1 || y > 10;
    }

    private void validateYCoordinate(int y) {
        if (isInvalidY(y)) {
            throw new IllegalArgumentException("세로 좌표는 1에서 10사이여야 합니다.");
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) object;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
            "x=" + x +
            ", y=" + y +
            '}';
    }
}
