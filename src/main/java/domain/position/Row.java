package domain.position;

import domain.position.exception.InvalidPositionException;
import domain.position.exception.PositionErrorMessage;

public record Row(int index) {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 9;
    private static final int ONE_SPACE = 1;

    public Row {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MINIMUM_BOUNDARY || index > MAXIMUM_BOUNDARY) {
            throw new InvalidPositionException(PositionErrorMessage.INVALID_ROW);
        }
    }

    public Row up() {
        return new Row(this.index + ONE_SPACE);
    }

    public Row down() {
        return new Row(this.index + Math.negateExact(ONE_SPACE));
    }

    public boolean isLowerThan(Row other) {
        return this.index < other.index;
    }

    public boolean isGapBiggerThanOne(Row other) {
        return Math.abs(this.index - other.index) > ONE_SPACE;
    }
}
