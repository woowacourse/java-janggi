package janggi.piece;

import janggi.position.Direction;

public enum Color {
    RED(Direction.DOWN),
    BLUE(Direction.UP);

    private final Direction front;

    Color(final Direction front) {
        this.front = front;
    }

    public boolean isReverseFrontVerticalDirection(Direction direction) {
        return front.isReverseFrontVerticalDirection(direction);
    }
}
