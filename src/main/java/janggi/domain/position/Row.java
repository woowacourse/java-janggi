package janggi.domain.position;

public record Row(
        int row
) {

    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;

    private static final String ROW_OUT_OF_BOUNDS_MESSAGE = "행은 %d부터 %d사이의 숫자입니다. 입력 값 : %d";

    public Row {
        validate(row);
    }

    private void validate(int row) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(String.format(ROW_OUT_OF_BOUNDS_MESSAGE, MIN_ROW, MAX_ROW, row));
        }
    }

    public static Row flippedIfNeeded(boolean isFlipped, int row) {
        if(isFlipped) {
            return new Row(MIN_ROW + MAX_ROW - row);
        }
        return new Row(row);
    }

    public Row add(int row) {
        return new Row(this.row + row);
    }

    public boolean isOffsetWithinBounds(int offset) {
        return this.row + offset >= MIN_ROW &&
                this.row + offset <= MAX_ROW;
    }
}
