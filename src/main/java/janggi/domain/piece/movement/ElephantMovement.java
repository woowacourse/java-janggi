package janggi.domain.piece.movement;

import static janggi.domain.piece.movement.Movement.DOWN;
import static janggi.domain.piece.movement.Movement.LEFT;
import static janggi.domain.piece.movement.Movement.RIGHT;
import static janggi.domain.piece.movement.Movement.UP;

import java.util.Arrays;
import java.util.List;

public enum ElephantMovement {
    UP_LEFT(
            new Movement(-3, -2),
            List.of(
                    UP,
                    UP.plus(UP).plus(LEFT)
            )
    ),
    UP_RIGHT(
            new Movement(-3, 2),
            List.of(
                    UP,
                    UP.plus(UP).plus(RIGHT)
            )
    ),
    DOWN_LEFT(
            new Movement(3, -2),
            List.of(
                    DOWN,
                    DOWN.plus(DOWN).plus(LEFT)
            )
    ),
    DOWN_RIGHT(
            new Movement(3, 2),
            List.of(
                    DOWN,
                    DOWN.plus(DOWN).plus(RIGHT)
            )
    ),
    LEFT_DOWN(
            new Movement(2, -3),
            List.of(
                    LEFT,
                    LEFT.plus(LEFT).plus(DOWN)
            )
    ),
    LEFT_UP(
            new Movement(-2, -3),
            List.of(
                    LEFT,
                    LEFT.plus(LEFT).plus(UP)
            )
    ),
    RIGHT_DOWN(
            new Movement(2, 3),
            List.of(
                    RIGHT,
                    RIGHT.plus(RIGHT).plus(DOWN)
            )
    ),
    RIGHT_UP(
            new Movement(-2, 3),
            List.of(
                    RIGHT,
                    RIGHT.plus(RIGHT).plus(UP)
            )
    );
    private final Movement relativeMovement;
    private final List<Movement> pathMovements;

    ElephantMovement(Movement relativeMovement, List<Movement> pathMovements) {
        this.relativeMovement = relativeMovement;
        this.pathMovements = pathMovements;
    }

    public static ElephantMovement getDirection(int x, int y) {
        Movement relativeMovementToMove = new Movement(x, y);
        return Arrays.stream(ElephantMovement.values())
                .filter(horseDirection -> horseDirection.relativeMovement.equals(relativeMovementToMove))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다."));
    }

    public List<Movement> getRouteDistances() {
        return pathMovements;
    }
}
