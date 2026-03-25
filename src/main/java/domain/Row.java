package domain;

public class Row {
    private static final int ROW_MAX_SIZE = 8;
    private final int row;

    public Row(int number) {
        validateRow(number);
        this.row = number;
    }

    private void validateRow(int number) {
        if (number > ROW_MAX_SIZE) {
            throw new IllegalArgumentException("행의 최대 값은 9입니다.");
        }
    }

    public int getValue() {
        return this.row;
    }
}
