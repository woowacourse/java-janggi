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
    private final Movement destinationMovement;
    private final Movement pathMovement;

    HorseMovement(Movement destinationMovement, Movement pathMovement) {
        this.destinationMovement = destinationMovement;
        this.pathMovement = pathMovement;
    }

    public static Movement findPathMovement(int x, int y) {
        return find(x, y).getPathMovement();
    }

    private static HorseMovement find(int x, int y) {
        Movement movement = new Movement(x, y);
        return Arrays.stream(HorseMovement.values())
                .filter(horseMovement -> horseMovement.destinationMovement.equals(movement))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("불가능한 이동입니다."));
    }

    private Movement getPathMovement() {
        return pathMovement;
    }
}
