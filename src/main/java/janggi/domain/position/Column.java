package janggi.domain.position;

import janggi.domain.exception.DomainException;

public record Column(
        int column
) {

    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 9;

    public static final String COLUMN_OUT_OF_BOUNDS_MESSAGE = "열은 %d부터 %d사이의 숫자입니다. 입력 값 : %d";

    public Column {
        validate(column);
    }

    private void validate(int column) {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new DomainException(
                    String.format(COLUMN_OUT_OF_BOUNDS_MESSAGE, MIN_COLUMN, MAX_COLUMN, column));
        }
    }

    public Column flip() {
        return new Column(MIN_COLUMN + MAX_COLUMN - column);
    }

    public Column add(int column) {
        return new Column(this.column + column);
    }

    public boolean isOffsetWithinBounds(int offset) {
        return this.column + offset >= MIN_COLUMN &&
                this.column + offset <= MAX_COLUMN;
    }
}
