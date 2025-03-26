package janggi.domain.piece.movement;

import static janggi.domain.piece.movement.Movement.DOWN;
import static janggi.domain.piece.movement.Movement.LEFT;
import static janggi.domain.piece.movement.Movement.RIGHT;
import static janggi.domain.piece.movement.Movement.UP;

import janggi.domain.piece.Position;
import java.util.Arrays;
import java.util.List;

public enum ElephantPathMovement {
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
    private final Movement destinationMovement;
    private final List<Movement> pathMovements;

    ElephantPathMovement(final Movement destinationMovement, final List<Movement> pathMovements) {
        this.destinationMovement = destinationMovement;
        this.pathMovements = pathMovements;
    }

    public static List<Movement> findPathMovements(final Position beforePosition, final Position afterPosition) {
        return find(afterPosition.x() - beforePosition.x(),
                afterPosition.y() - beforePosition.y()).pathMovements;
    }

    private static ElephantPathMovement find(final int x, final int y) {
        Movement movement = new Movement(x, y);
        return Arrays.stream(ElephantPathMovement.values())
                .filter(horseDirection -> horseDirection.destinationMovement.equals(movement))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다."));
    }
}
