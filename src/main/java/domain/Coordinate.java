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

    private void validateXCoordinate(int x) {
        if (x < 1 || x > 9) {
            throw new IllegalArgumentException("가로 좌표는 1에서 9사이여야 합니다.");
        }
    }

    private void validateYCoordinate(int y) {
        if (y < 1 || y > 10) {
            throw new IllegalArgumentException("세로 좌표는 1에서 10사이여야 합니다.");
        }
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
