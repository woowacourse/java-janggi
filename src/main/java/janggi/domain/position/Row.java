package janggi.domain.position;

import java.util.Arrays;

public record Row(
        int row
) {

    public static final int MIN_ROW = 1;
    public static final int MAX_ROW = 10;

    private static final Row[] ROWS = new Row[MAX_ROW + 1];

    static {
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            ROWS[row] = new Row(row);
        }
    }

    public Row {
        validate(row);
    }

    public static Row of(int row) {
        validate(row);
        return ROWS[row];
    }

    public static Row[] values() {
        return Arrays.copyOfRange(ROWS, MIN_ROW, MAX_ROW + 1);
    }

    private static void validate(int row) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException(String.format("행은 %d부터 %d사이의 숫자입니다.", MIN_ROW, MAX_ROW));
        }
    }

    public Row add(int row) {
        return Row.of(this.row + row);
    }

}
