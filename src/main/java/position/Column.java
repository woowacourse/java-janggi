package position;

public record Column(int index) {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;
    private static final int ONE_SPACE = 1;

    public Column {
        validateRange(index);
    }

    private void validateRange(final int index) {
        if (!isValidRange(index)) {
            throw new IllegalArgumentException("유효하지 않은 COLUMN입니다.");
        }
    }

    private boolean isValidRange(final int index) {
        return MINIMUM_BOUNDARY <= index && index <= MAXIMUM_BOUNDARY;
    }

    public boolean isLeft(Column column) {
        return this.index < column.index;
    }

    public boolean isRight(Column column) {
        return this.index > column.index;
    }

    public boolean isGapBiggerThanOne(Column other) {
        return Math.abs(this.index - other.index) > ONE_SPACE;
    }

    public boolean canMove(Delta delta) {
        int nextColumn = index + delta.columnDelta();
        return isValidRange(nextColumn);
    }

    public Column move(Delta delta) {
        return new Column(index + delta.columnDelta());
    }
}
