package janggi.domain.position;

public record Column(
        int column
) {

    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 9;

    public Column {
        validate(column);
    }

    private void validate(int column) {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException(String.format("열은 %d부터 %d사이의 숫자입니다.", MIN_COLUMN, MAX_COLUMN));
        }
    }

    public Column add(int column) {
        return new Column(this.column + column);
    }

    public boolean isOffsetWithinBounds(int offset) {
        return this.column + offset >= MIN_COLUMN &&
                this.column + offset <= MAX_COLUMN;
    }
}
