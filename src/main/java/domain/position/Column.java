package domain.position;

import domain.position.exception.InvalidPositionException;
import domain.position.exception.PositionErrorMessage;

public record Column(int index) {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;
    private static final int ONE_SPACE = 1;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MINIMUM_BOUNDARY || index > MAXIMUM_BOUNDARY) {
            throw new InvalidPositionException(PositionErrorMessage.INVALID_COLUMN);
        }
    }

    public Column right() {
        return new Column(this.index + ONE_SPACE);
    }

    public Column left() {
        return new Column(this.index + Math.negateExact(ONE_SPACE));
    }

    public boolean isLeft(Column column) {
        return this.index < column.index;
    }

    public boolean isGapBiggerThanOne(Column other) {
        return Math.abs(this.index - other.index) > ONE_SPACE;
    }
}
