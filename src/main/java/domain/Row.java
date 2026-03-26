package domain;

public record Row(int value) {
    private static final int ROW_MAX_SIZE = 8;

    public Row {
        validateRow(value);
    }

    private void validateRow(int value) {
        if (value > ROW_MAX_SIZE) {
            throw new IllegalArgumentException("행의 최대 값은 " + ROW_MAX_SIZE + "입니다.");
        }
    }
}