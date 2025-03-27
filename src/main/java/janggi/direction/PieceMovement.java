package janggi.direction;

import static janggi.direction.Direction.DOWN;
import static janggi.direction.Direction.DOWN_LEFT;
import static janggi.direction.Direction.DOWN_RIGHT;
import static janggi.direction.Direction.LEFT;
import static janggi.direction.Direction.RIGHT;
import static janggi.direction.Direction.UP;
import static janggi.direction.Direction.UP_LEFT;
import static janggi.direction.Direction.UP_RIGHT;

import java.util.List;

public enum PieceMovement {

    CANNON(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    ))),
    CHARIOT(new Movements(List.of(
            new Movement(UP),
            new Movement(DOWN),
            new Movement(RIGHT),
            new Movement(LEFT)
    ))),
    CHO_SOLDIER(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT)
    ))),
    HAN_SOLDIER(new Movements(List.of(
            new Movement(DOWN),
            new Movement(RIGHT),
            new Movement(LEFT)
    ))),
    ELEPHANT(new Movements(List.of(
            new Movement(DOWN, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT, DOWN_LEFT),
            new Movement(UP, UP_RIGHT, UP_RIGHT),
            new Movement(UP, UP_LEFT, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT, UP_LEFT)
    ))),
    GUARD(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    ))),
    HORSE(new Movements(List.of(
            new Movement(DOWN, DOWN_RIGHT),
            new Movement(DOWN, DOWN_LEFT),
            new Movement(UP, UP_RIGHT),
            new Movement(UP, UP_LEFT),
            new Movement(RIGHT, DOWN_RIGHT),
            new Movement(LEFT, DOWN_LEFT),
            new Movement(RIGHT, UP_RIGHT),
            new Movement(LEFT, UP_LEFT)
    ))),
    KING(new Movements(List.of(
            new Movement(UP),
            new Movement(RIGHT),
            new Movement(LEFT),
            new Movement(DOWN)
    )));

    private final Movements movements;

    PieceMovement(final Movements movements) {
        this.movements = movements;
    }

    public Movements getMovements() {
        return movements;
    }
}
