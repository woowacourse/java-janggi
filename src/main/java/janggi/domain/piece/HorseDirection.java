package janggi.domain.piece;

import java.util.Arrays;

public enum HorseDirection {
    UP_LEFT(
            new Distance(-2, -1),
            new Distance(-1, 0)
    ),
    UP_RIGHT(
            new Distance(-2, 1),
            new Distance(-1, 0)
    ),
    DOWN_LEFT(
            new Distance(2, -1),
            new Distance(1, 0)
    ),
    DOWN_RIGHT(
            new Distance(2, 1),
            new Distance(1, 0)
    ),
    LEFT_DOWN(
            new Distance(1, -2),
            new Distance(0, -1)
    ),
    LEFT_UP(
            new Distance(-1, -2),
            new Distance(0, -1)
    ),
    RIGHT_DOWN(
            new Distance(1, 2),
            new Distance(0, 1)
    ),
    RIGHT_UP(
            new Distance(-1, 2),
            new Distance(0, 1)
    );
    private final Distance relativeDistanceToMove;
    private final Distance routeDistance;

    HorseDirection(Distance relativeDistanceToMove, Distance routeDistance) {
        this.relativeDistanceToMove = relativeDistanceToMove;
        this.routeDistance = routeDistance;
    }

    public static HorseDirection getDirection(int x, int y) {
        Distance relativeDistanceToMove = new Distance(x, y);

        return Arrays.stream(HorseDirection.values())
                .filter(horseDirection -> horseDirection.relativeDistanceToMove.equals(relativeDistanceToMove))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public Distance getRouteDistance() {
        return routeDistance;
    }
}
