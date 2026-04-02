package janggi.domain.dynasty;

import janggi.domain.position.Column;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
import janggi.domain.position.Row;

public enum Dynasty {

    CHO(Direction.SOUTH, false, 0),
    HAN(Direction.NORTH, true, 1.5);

    private final Direction front;
    private final boolean isFlipped;
    private final double additionalScore;

    Dynasty(Direction front, boolean isFlipped, double additionalScore) {
        this.front = front;
        this.isFlipped = isFlipped;
        this.additionalScore = additionalScore;
    }

    public boolean isFlipped() {
        return isFlipped;
    }

    public Direction front() {
        return front;
    }

    public Dynasty next() {
        Dynasty[] values = Dynasty.values();
        return values[(ordinal() + 1) % values.length];
    }

    public double additionalScore() {
        return additionalScore;
    }

    public Row flipRowIfNeeded(Row row) {
        if(isFlipped) {
            return row.flip();
        }
        return row;
    }

}
