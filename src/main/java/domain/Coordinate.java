package domain;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Coordinate {

    private static final Map<Coordinate, Set<Movement>> CASTLE_DIAGONAL_CONNECTIONS = Map.of(
        new Coordinate(4, 1), Set.of(Movement.RIGHT_DOWN),
        new Coordinate(4, 3), Set.of(Movement.RIGHT_UP),
        new Coordinate(6, 1), Set.of(Movement.LEFT_DOWN),
        new Coordinate(6, 3), Set.of(Movement.LEFT_UP),
        new Coordinate(5, 2), Set.of(Movement.LEFT_UP, Movement.RIGHT_UP, Movement.LEFT_DOWN, Movement.RIGHT_DOWN),

        new Coordinate(4, 8), Set.of(Movement.RIGHT_DOWN),
        new Coordinate(4, 10), Set.of(Movement.RIGHT_UP),
        new Coordinate(6, 8), Set.of(Movement.LEFT_DOWN),
        new Coordinate(6, 10), Set.of(Movement.LEFT_UP),
        new Coordinate(5, 9), Set.of(Movement.LEFT_UP, Movement.RIGHT_UP, Movement.LEFT_DOWN, Movement.RIGHT_DOWN)
    );

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

    public boolean isInCastle() {
        Set<Integer> xCoordinates = Set.of(4, 5, 6);
        Set<Integer> yCoordinates = Set.of(1, 2, 3, 8, 9, 10);

        return xCoordinates.contains(this.x)
            && yCoordinates.contains(this.y);
    }

    public Set<Movement> getMovementsIfInCastle() {
        return CASTLE_DIAGONAL_CONNECTIONS.getOrDefault(this, Set.of());
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
