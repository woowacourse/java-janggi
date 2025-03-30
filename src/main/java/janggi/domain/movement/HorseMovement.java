package janggi.domain.movement;

import static janggi.domain.movement.Movement.DOWN;
import static janggi.domain.movement.Movement.LEFT;
import static janggi.domain.movement.Movement.LEFT_DOWN;
import static janggi.domain.movement.Movement.LEFT_UP;
import static janggi.domain.movement.Movement.RIGHT;
import static janggi.domain.movement.Movement.RIGHT_DOWN;
import static janggi.domain.movement.Movement.RIGHT_UP;
import static janggi.domain.movement.Movement.UP;

public enum HorseMovement {

    UP_UP_LEFT(UP, LEFT_UP),
    UP_UP_RIGHT(UP, RIGHT_UP),
    DOWN_DOWN_LEFT(DOWN, LEFT_DOWN),
    DOWN_DOWN_RIGHT(DOWN, RIGHT_DOWN),
    LEFT_LEFT_UP(LEFT, LEFT_UP),
    LEFT_LEFT_DOWN(LEFT, LEFT_DOWN),
    RIGHT_RIGHT_UP(RIGHT, RIGHT_UP),
    RIGHT_RIGHT_DOWN(RIGHT, RIGHT_DOWN);

    private final Movement first;
    private final Movement second;

    HorseMovement(final Movement first, final Movement second) {
        this.first = first;
        this.second = second;
    }

    public int columnValue() {
        return first.columnValue() + second.columnValue();
    }

    public int rowValue() {
        return first.rowValue() + second.rowValue();
    }

    public Movement getFirst() {
        return first;
    }
}
