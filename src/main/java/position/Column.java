package position;

public record Column(int index) {
    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;
    private static final int ONE_SPACE = 1;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MINIMUM_BOUNDARY || index > MAXIMUM_BOUNDARY) {
            throw new IllegalArgumentException("유효하지 않은 COLUMN입니다.");
        }
    }

    public Column right() {
        return new Column(this.index + ONE_SPACE);
    }

    public Column left() {
        return new Column(this.index + Math.negateExact(ONE_SPACE));
    }
}
