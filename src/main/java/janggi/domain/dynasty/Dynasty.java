package janggi.domain.dynasty;

import janggi.domain.position.Direction;
import janggi.domain.position.Row;

public enum Dynasty {

    CHO(Direction.SOUTH, false),
    HAN(Direction.NORTH, true);

    private final Direction front;
    private final boolean isFlipped;

    Dynasty(Direction front, boolean isFlipped) {
        this.front = front;
        this.isFlipped = isFlipped;
    }

    public Direction front() {
        return front;
    }

    public Dynasty next() {
        Dynasty[] values = Dynasty.values();
        return values[(ordinal() + 1) % values.length];
    }

    public int flipRowIfNeeded(int row) {
        return Row.flippedIfNeeded(isFlipped, row)
                .row();
    }
    
}
