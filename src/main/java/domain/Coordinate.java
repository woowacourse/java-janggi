package domain;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public record Coordinate(int x, int y) {

    public static final Set<Integer> CASTLE_X_COORDINATES = Set.of(4, 5, 6);
    public static final Set<Integer> CASTLE_Y_COORDINATES = Set.of(1, 2, 3, 8, 9, 10);

    private static final Map<Coordinate, Set<Coordinate>> CASTLE_DIAGONAL_CONNECTIONS = Map.of(
        new Coordinate(4, 1), Set.of(new Coordinate(5, 2)),
        new Coordinate(4, 3), Set.of(new Coordinate(5, 2)),
        new Coordinate(6, 1), Set.of(new Coordinate(5, 2)),
        new Coordinate(6, 3), Set.of(new Coordinate(5, 2)),
        new Coordinate(5, 2), Set.of(new Coordinate(4,1), new Coordinate(4, 3), new Coordinate(6, 1), new Coordinate(6, 3)),

        new Coordinate(4, 8), Set.of(new Coordinate(5, 9)),
        new Coordinate(4, 10), Set.of(new Coordinate(5, 9)),
        new Coordinate(6, 8), Set.of(new Coordinate(5, 9)),
        new Coordinate(6, 10), Set.of(new Coordinate(5, 9)),
        new Coordinate(5, 9), Set.of(new Coordinate(4, 8), new Coordinate(4, 10), new Coordinate(6, 8), new Coordinate(6, 10))
    );

    public Coordinate {
        validateXCoordinate(x);
        validateYCoordinate(y);
    }

    public boolean canMove(MoveVector moveVector) {
        int newX = this.x + moveVector.deltaX();
        int newY = this.y + moveVector.deltaY();
        return !isInvalidX(newX) && !isInvalidY(newY);
    }

    public Coordinate move(MoveVector moveVector) {
        int newX = this.x + moveVector.deltaX();
        int newY = this.y + moveVector.deltaY();

        return new Coordinate(newX, newY);
    }

    public boolean isInCastle() {
        return CASTLE_X_COORDINATES.contains(this.x)
            && CASTLE_Y_COORDINATES.contains(this.y);
    }

    public Set<Coordinate> findCastleConnections() {
        return CASTLE_DIAGONAL_CONNECTIONS.getOrDefault(this, Collections.emptySet());
    }

    public MoveVector computeMovementTo(Coordinate destination) {
        return new MoveVector(destination.x - this.x, destination.y - this.y);
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
}
