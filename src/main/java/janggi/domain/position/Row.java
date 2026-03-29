package janggi.domain.position;

public record Row(
        int row
) {

    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;

    public Row {
        validate(row);
    }

    private void validate(int row) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(String.format("행은 %d부터 %d사이의 숫자입니다.", MIN_ROW, MAX_ROW));
        }
    }

    public Row add(int row) {
        return new Row(this.row + row);
    }

    public boolean isOffsetWithinBounds(int offset) {
        return this.row + offset >= MIN_ROW &&
                this.row + offset <= MAX_ROW;
    }
}
