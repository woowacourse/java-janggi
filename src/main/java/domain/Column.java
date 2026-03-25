package domain;

public class Column {
    private static final int COLUMN_MAX_SIZE = 8;
    private final int column;

    public Column(int number) {
        validateColumn(number);
        this.column = number;
    }

    public int getValue() {
        return this.column;
    }

    private void validateColumn(int number) {
        if (number > COLUMN_MAX_SIZE) {
            throw new IllegalArgumentException("열의 최대 값은 10입니다.");
        }
    }
}
