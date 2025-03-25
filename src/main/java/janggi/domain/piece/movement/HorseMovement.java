package janggi.domain.piece.movement;

import static janggi.domain.piece.movement.Movement.DOWN;
import static janggi.domain.piece.movement.Movement.LEFT;
import static janggi.domain.piece.movement.Movement.RIGHT;
import static janggi.domain.piece.movement.Movement.UP;

import java.util.Arrays;

public enum HorseMovement {
    UP_LEFT(
            new Movement(-2, -1),
            UP
    ),
    UP_RIGHT(
            new Movement(-2, 1),
            UP
    ),
    DOWN_LEFT(
            new Movement(2, -1),
            DOWN
    ),
    DOWN_RIGHT(
            new Movement(2, 1),
            DOWN
    ),
    LEFT_DOWN(
            new Movement(1, -2),
            LEFT
    ),
    LEFT_UP(
            new Movement(-1, -2),
            LEFT
    ),
    RIGHT_DOWN(
            new Movement(1, 2),
            RIGHT
    ),
    RIGHT_UP(
            new Movement(-1, 2),
            UP
    );
    private final Movement relativeMovement;
    private final Movement pathMovement;

    HorseMovement(Movement relativeMovement, Movement pathMovement) {
        this.relativeMovement = relativeMovement;
        this.pathMovement = pathMovement;
    }

    public static HorseMovement getDirection(int x, int y) {
        Movement relativeMovementToMove = new Movement(x, y);
        return Arrays.stream(HorseMovement.values())
                .filter(horseMovement -> horseMovement.relativeMovement.equals(relativeMovementToMove))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다."));
    }

    public Movement getRouteDistance() {
        return pathMovement;
    }
}
