package janggi.domain.position;

import java.util.Arrays;

public record Column(
        int column
) {

    public static final int MIN_COLUMN = 1;
    public static final int MAX_COLUMN = 9;

    private static final Column[] COLUMNS = new Column[MAX_COLUMN + 1];

    static {
        for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
            COLUMNS[column] = new Column(column);
        }
    }

    public Column {
        validate(column);
    }

    public static Column of(int column) {
        validate(column);
        return COLUMNS[column];
    }

    public static Column[] values() {
        return Arrays.copyOfRange(COLUMNS, MIN_COLUMN, MAX_COLUMN + 1);
    }

    private static void validate(int column) {
        if (column < MIN_COLUMN || column > MAX_COLUMN) {
            throw new IllegalArgumentException(String.format("열은 %d부터 %d사이의 숫자입니다.", MIN_COLUMN, MAX_COLUMN));
        }
    }

    public Column add(int column) {
        return Column.of(this.column + column);
    }

}
