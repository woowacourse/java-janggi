package domain.board;

public record Column(int value) {
    private static final int COLUMN_MIN_SIZE = 0;
    private static final int COLUMN_MAX_SIZE = 8;

    public Column {
        validateColumn(value);
    }

    private void validateColumn(int number) {
        if (number < COLUMN_MIN_SIZE) {
            throw new IllegalArgumentException("열의 최소 값은 " + COLUMN_MIN_SIZE + "입니다.");
        }
        if (number > COLUMN_MAX_SIZE) {
            throw new IllegalArgumentException("열의 최대 값은 " + COLUMN_MAX_SIZE + "입니다.");
        }
    }
}



