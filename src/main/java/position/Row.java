package position;

public record Row(int index) {

    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 9;
    private static final int ONE_SPACE = 1;

    public Row {
        validateRange(index);
    }

    private void validateRange(final int index) {
        if (!isValidRange(index)) {
            throw new IllegalArgumentException("유효하지 않은 ROW입니다.");
        }
    }

    private boolean isValidRange(final int index) {
        return MINIMUM_BOUNDARY <= index && index <= MAXIMUM_BOUNDARY;
    }

    public boolean isInRange(final Row min, final Row max) {
        return min.index <= index && index <= max.index;
    }

    public boolean isGapBiggerThanOne(final Row other) {
        return Math.abs(this.index - other.index) > ONE_SPACE;
    }

    public Row add(final Delta delta) {
        return new Row(index + delta.rowDelta());
    }

    public boolean canMove(final Delta delta) {
        final int nextIndex = index + delta.rowDelta();
        return isValidRange(nextIndex);
    }

    public Row reverse() {
        return new Row(MAXIMUM_BOUNDARY - index);
    }

    public Delta calculateDelta(final Row row) {
        return new Delta(index - row.index, 0);
    }
}
