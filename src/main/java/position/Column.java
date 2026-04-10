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

    public boolean isInRange(final Column min, final Column max) {
        return min.index <= index && index <= max.index;
    }

    public boolean isGapBiggerThanOne(final Column other) {
        return Math.abs(this.index - other.index) > ONE_SPACE;
    }

    public boolean canMove(final Delta delta) {
        int nextColumn = index + delta.columnDelta();
        return isValidRange(nextColumn);
    }

    public Column move(final Delta delta) {
        return new Column(index + delta.columnDelta());
    }

    public Column reverse() {
        return new Column(MAXIMUM_BOUNDARY - index);
    }

    public Delta calculateDelta(final Column column) {
        return new Delta(0, index - column.index);
    }
}
