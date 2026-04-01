package janggi.domain.dynasty;

import janggi.domain.position.Column;
import janggi.domain.position.Direction;
import janggi.domain.position.Position;
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

    public Row flipRowIfNeeded(Row row) {
        if(isFlipped) {
            return row.flip();
        }
        return row;
    }

    public Position flipPositionIfNeeded(Position position) {
        if(isFlipped) {
            return position.flip();
        }

        return position;
    }

}
