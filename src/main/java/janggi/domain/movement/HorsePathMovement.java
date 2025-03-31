package janggi.domain.movement;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.UP;

import janggi.domain.piece.Position;
import java.util.Arrays;
import java.util.List;

public enum HorsePathMovement {
    UP_LEFT(
            new Movement(-2, -1),
            List.of(UP)
    ),
    UP_RIGHT(
            new Movement(-2, 1),
            List.of(UP)
    ),
    DOWN_LEFT(
            new Movement(2, -1),
            List.of(DOWN)
    ),
    DOWN_RIGHT(
            new Movement(2, 1),
            List.of(DOWN)
    ),
    LEFT_DOWN(
            new Movement(1, -2),
            List.of(LEFT)
    ),
    LEFT_UP(
            new Movement(-1, -2),
            List.of(LEFT)
    ),
    RIGHT_DOWN(
            new Movement(1, 2),
            List.of(RIGHT)
    ),
    RIGHT_UP(
            new Movement(-1, 2),
            List.of(UP)
    );
    private final Movement destinationMovement;
    private final List<Movement> pathMovements;

    HorsePathMovement(final Movement destinationMovement, final List<Movement> pathMovements) {
        this.destinationMovement = destinationMovement;
        this.pathMovements = pathMovements;
    }

    public static List<Movement> findPathMovements(final Position beforePosition, final Position afterPosition) {
        Movement movement = afterPosition.getMovementTo(beforePosition);
        return find(movement.x(), movement.y()).pathMovements;
    }

    private static HorsePathMovement find(final int x, final int y) {
        Movement movement = new Movement(x, y);
        return Arrays.stream(HorsePathMovement.values())
                .filter(horsePathMovement -> horsePathMovement.destinationMovement.equals(movement))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다."));
    }
}
