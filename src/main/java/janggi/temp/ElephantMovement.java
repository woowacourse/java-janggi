package janggi.temp;

import static janggi.temp.Movement.DOWN;
import static janggi.temp.Movement.LEFT;
import static janggi.temp.Movement.LEFT_DOWN;
import static janggi.temp.Movement.LEFT_UP;
import static janggi.temp.Movement.RIGHT;
import static janggi.temp.Movement.RIGHT_DOWN;
import static janggi.temp.Movement.RIGHT_UP;
import static janggi.temp.Movement.UP;

public enum ElephantMovement {

    UP_UP_UP_LEFT(UP, UP, LEFT_UP),
    UP_UP_UP_RIGHT(UP, UP, RIGHT_UP),
    DOWN_DOWN_DOWN_LEFT(DOWN, DOWN, LEFT_DOWN),
    DOWN_DOWN_DOWN_RIGHT(DOWN, DOWN, RIGHT_DOWN),
    LEFT_LEFT_LEFT_UP(LEFT, LEFT, LEFT_UP),
    LEFT_LEFT_LEFT_DOWN(LEFT, LEFT, LEFT_DOWN),
    RIGHT_RIGHT_RIGHT_UP(RIGHT, RIGHT, RIGHT_UP),
    RIGHT_RIGHT_RIGHT_DOWN(RIGHT, RIGHT, RIGHT_DOWN);

    private final Movement first;
    private final Movement second;
    private final Movement third;

    ElephantMovement(final Movement first, final Movement second, final Movement third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public int columnValue() {
        return first.columnValue() + second.columnValue() + third.columnValue();
    }

    public int rowValue() {
        return first.rowValue() + second.rowValue() + third.rowValue();
    }

    public Movement getFirst() {
        return first;
    }

    public Movement getSecond() {
        return second;
    }
}
