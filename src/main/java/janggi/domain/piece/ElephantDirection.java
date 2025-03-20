package janggi.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum ElephantDirection {
    UP_LEFT(
            new Position(-3, -2),
            List.of(
                    new Position(-1, 0),
                    new Position(-2, -1)
            )
    ),
    UP_RIGHT(
            new Position(-3, 2),
            List.of(
                    new Position(-1, 0),
                    new Position(-2, 1)
            )
    ),
    DOWN_LEFT(
            new Position(3, -2),
            List.of(
                    new Position(1, 0),
                    new Position(2, -1)
            )
    ),
    DOWN_RIGHT(
            new Position(3, 2),
            List.of(
                    new Position(1, 0),
                    new Position(2, 1)
            )
    ),
    LEFT_DOWN(
            new Position(2, -3),
            List.of(
                    new Position(0, -1),
                    new Position(1, -2)
            )
    ),
    LEFT_UP(
            new Position(-2, -3),
            List.of(
                    new Position(0, -1),
                    new Position(-1, -2)
            )
    ),
    RIGHT_DOWN(
            new Position(2, 3),
            List.of(
                    new Position(0, 1),
                    new Position(1, 2)
            )
    ),
    RIGHT_UP(
            new Position(-2, 3),
            List.of(
                    new Position(-1, 2),
                    new Position(0, 1)
            )
    );
    private final Position relativePositionToMove;
    private final List<Position> routePositions;

    ElephantDirection(Position relativePositionToMove, List<Position> routePositions) {
        this.relativePositionToMove = relativePositionToMove;
        this.routePositions = routePositions;
    }

    public static ElephantDirection getDirection(int x, int y) {
        Position relativePositionToMove = new Position(x, y);

        return Arrays.stream(ElephantDirection.values())
                .filter(horseDirection -> horseDirection.relativePositionToMove.equals(relativePositionToMove))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Position> getRoutePositions() {
        return routePositions;
    }
}
