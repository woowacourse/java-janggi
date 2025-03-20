package janggi.domain.piece;

import java.util.Arrays;

public enum HorseDirection {
    UP_LEFT(
            new Position(-2, -1),
            new Position(-1, 0)
    ),
    UP_RIGHT(
            new Position(-2, 1),
            new Position(-1, 0)
    ),
    DOWN_LEFT(
            new Position(2, -1),
            new Position(1, 0)
    ),
    DOWN_RIGHT(
            new Position(2, 1),
            new Position(1, 0)
    ),
    LEFT_DOWN(
            new Position(1, -2),
            new Position(0, -1)
    ),
    LEFT_UP(
            new Position(-1, -2),
            new Position(0, -1)
    ),
    RIGHT_DOWN(
            new Position(1, 2),
            new Position(0, 1)
    ),
    RIGHT_UP(
            new Position(-1, 2),
            new Position(0, 1)
    );
    private final Position relativePositionToMove;
    private final Position routePosition;

    HorseDirection(Position relativePositionToMove, Position routePosition) {
        this.relativePositionToMove = relativePositionToMove;
        this.routePosition = routePosition;
    }

    public static HorseDirection getDirection(int x, int y) {
        Position relativePositionToMove = new Position(x, y);

        return Arrays.stream(HorseDirection.values())
                .filter(horseDirection -> horseDirection.relativePositionToMove.equals(relativePositionToMove))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Position getRoutePosition() {
        return routePosition;
    }
}
