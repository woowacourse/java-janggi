package janggi.domain.piece;

import java.util.Arrays;
import java.util.List;

public enum ElephantDirection {
    UP_LEFT(
            new Distance(-3, -2),
            List.of(
                    new Distance(-1, 0),
                    new Distance(-2, -1)
            )
    ),
    UP_RIGHT(
            new Distance(-3, 2),
            List.of(
                    new Distance(-1, 0),
                    new Distance(-2, 1)
            )
    ),
    DOWN_LEFT(
            new Distance(3, -2),
            List.of(
                    new Distance(1, 0),
                    new Distance(2, -1)
            )
    ),
    DOWN_RIGHT(
            new Distance(3, 2),
            List.of(
                    new Distance(1, 0),
                    new Distance(2, 1)
            )
    ),
    LEFT_DOWN(
            new Distance(2, -3),
            List.of(
                    new Distance(0, -1),
                    new Distance(1, -2)
            )
    ),
    LEFT_UP(
            new Distance(-2, -3),
            List.of(
                    new Distance(0, -1),
                    new Distance(-1, -2)
            )
    ),
    RIGHT_DOWN(
            new Distance(2, 3),
            List.of(
                    new Distance(0, 1),
                    new Distance(1, 2)
            )
    ),
    RIGHT_UP(
            new Distance(-2, 3),
            List.of(
                    new Distance(-1, 2),
                    new Distance(0, 1)
            )
    );
    private final Distance relativeDistanceToMove;
    private final List<Distance> routeDistances;

    ElephantDirection(Distance relativeDistanceToMove, List<Distance> routeDistances) {
        this.relativeDistanceToMove = relativeDistanceToMove;
        this.routeDistances = routeDistances;
    }

    public static ElephantDirection getDirection(int x, int y) {
        Distance relativeDistanceToMove = new Distance(x, y);

        return Arrays.stream(ElephantDirection.values())
                .filter(horseDirection -> horseDirection.relativeDistanceToMove.equals(relativeDistanceToMove))
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    public List<Distance> getRouteDistances() {
        return routeDistances;
    }
}
