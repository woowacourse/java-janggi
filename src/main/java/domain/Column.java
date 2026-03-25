package domain;

public record Column(int index) {
    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 8;

    public Column {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MINIMUM_BOUNDARY || index > MAXIMUM_BOUNDARY) {
            throw new IllegalArgumentException("유효하지 않은 COLUMN입니다.");
        }
    }
}
