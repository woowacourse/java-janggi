package coordinate;

import static constant.JanggiConstant.BOARD_MAX_HEIGHT;
import static constant.JanggiConstant.BOARD_MAX_WIDTH;
import static constant.JanggiConstant.BOARD_MIN_HEIGHT;
import static constant.JanggiConstant.BOARD_MIN_WIDTH;

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
        for (int x = BOARD_MIN_WIDTH; x <= BOARD_MAX_WIDTH; x++) {
            coordinates.add(new Coordinate(x, this.y));
        }
        for (int y = BOARD_MIN_HEIGHT; y <= BOARD_MAX_HEIGHT; y++) {
            coordinates.add(new Coordinate(this.x, y));
        }
        coordinates.remove(this);
        return coordinates;
    }

    private boolean isInvalidX(int x) {
        return x < BOARD_MIN_WIDTH || x > BOARD_MAX_WIDTH;
    }

    private void validateXCoordinate(int x) {
        if (isInvalidX(x)) {
            throw new IllegalArgumentException(
                    String.format("가로 좌표는 %d에서 %d사이여야 합니다.", BOARD_MIN_WIDTH, BOARD_MAX_WIDTH));
        }
    }

    private static boolean isInvalidY(int y) {
        return y < BOARD_MIN_HEIGHT || y > BOARD_MAX_HEIGHT;
    }

    private void validateYCoordinate(int y) {
        if (isInvalidY(y)) {
            throw new IllegalArgumentException(
                    String.format("세로 좌표는 %d에서 %d사이여야 합니다.", BOARD_MIN_HEIGHT, BOARD_MAX_HEIGHT));
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
        return "coordinate.Coordinate{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
