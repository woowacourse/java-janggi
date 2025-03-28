package coordinate;

import static board.Board.BOARD_MAX_HEIGHT;
import static board.Board.BOARD_MAX_WIDTH;
import static board.Board.BOARD_MIN_HEIGHT;
import static board.Board.BOARD_MIN_WIDTH;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class Coordinate {

    private final int x;
    private final int y;

    public Coordinate(int x, int y) {
        validateXCoordinate(x);
        validateYCoordinate(y);
        this.x = x;
        this.y = y;
    }

    public Coordinate moveBy(MoveVector moveVector) {
        int deltaX = moveVector.deltaX();
        int deltaY = moveVector.deltaY();

        int newX = this.x + deltaX;
        int newY = this.y + deltaY;

        if (isInvalidX(newX) || isInvalidY(newY)) {
            return null;
        }
        return new Coordinate(newX, newY);
    }

    public Coordinate moveBy(List<MoveVector> moveVectors) {
        int deltaX = moveVectors.stream()
                .mapToInt(MoveVector::deltaX)
                .sum();
        int deltaY = moveVectors.stream()
                .mapToInt(MoveVector::deltaY)
                .sum();

        int newX = this.x + deltaX;
        int newY = this.y + deltaY;

        if (isInvalidX(newX) || isInvalidY(newY)) {
            return null;
        }
        return new Coordinate(newX, newY);
    }

    public Set<Coordinate> moveByCross() {
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

    public Set<Coordinate> moveByCrossOne() {
        return Arrays.stream(Direction.values())
                .map(this::moveBy)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    public Set<Coordinate> moveByDiagonalInCastle() {
        Set<Coordinate> coordinates = new HashSet<>();
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
            Coordinate current = this;
            while (true) {
                Coordinate next = current.moveBy(diagonalDirection);
                if (Objects.isNull(next) || !next.isInCastle()) {
                    break;
                }
                coordinates.add(next);
                current = next;
            }
        }
        return coordinates;
    }

    public Set<Coordinate> moveByDiagonalOneInCastle() {
        Set<Coordinate> coordinates = new HashSet<>();
        for (DiagonalDirection diagonalDirection : DiagonalDirection.values()) {
            Coordinate next = this.moveBy(diagonalDirection);
            if (Objects.isNull(next) || !next.isInCastle()) {
                continue;
            }
            coordinates.add(next);
        }
        return coordinates;
    }

    public boolean isInCastle() {
        if ((x >= 4 && x <= 6) && (y >= 1 && y <= 3)) {
            return true;
        }
        if ((x >= 4 && x <= 6) && (y >= 8 && y <= 10)) {
            return true;
        }
        return false;
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

    private boolean isInvalidY(int y) {
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
