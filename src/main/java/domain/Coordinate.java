package domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        validateXCoordinate(x);
        validateYCoordinate(y);
        this.x = x;
        this.y = y;
    }

    public Optional<Coordinate> pickChangedCoordinate(int x, int y) {
        if (isInvalidX(this.x + x) || isInvalidY(this.y + y)) {
            return Optional.empty();
        }
        return Optional.of(new Coordinate(this.x + x, this.y + y));
    }

    public Set<Coordinate> pickCrossCoordinates() {
        Set<Coordinate> coordinates = new HashSet<>();
        for (int x = 1; x <= 9; x++) {
            coordinates.add(new Coordinate(x, this.y));
        }
        for (int y = 1; y <= 10; y++) {
            coordinates.add(new Coordinate(this.x, y));
        }
        coordinates.remove(this);
        return coordinates;
    }

    private boolean isInvalidX(int x) {
        return x < 1 || x > 9;
    }

    private void validateXCoordinate(int x) {
        if (isInvalidX(x)) {
            throw new IllegalArgumentException("가로 좌표는 1에서 9사이여야 합니다.");
        }
    }

    private static boolean isInvalidY(int y) {
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
