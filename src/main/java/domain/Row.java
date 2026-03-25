package domain;

public record Row(int index) {
    private static final int MINIMUM_BOUNDARY = 0;
    private static final int MAXIMUM_BOUNDARY = 9;

    public Row {
        validateRange(index);
    }

    private void validateRange(int index) {
        if (index < MINIMUM_BOUNDARY || index > MAXIMUM_BOUNDARY) {
            throw new IllegalArgumentException("유효하지 않은 ROW입니다.");
        }
    }
}
