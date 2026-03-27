package domain;

public record Column(int value) {
    private static final int COLUMN_MAX_SIZE = 8;

    public Column {
        validateColumn(value);
    }

    private void validateColumn(int number) {
        if (number > COLUMN_MAX_SIZE) {
            throw new IllegalArgumentException("열의 최대 값은 " + COLUMN_MAX_SIZE + "입니다.");
        }
    }
}
