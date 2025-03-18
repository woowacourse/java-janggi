package domain;

import java.util.Objects;

public class Row {
    private static final int MAX_ROW = 9;
    private static final int MIN_ROW = 0;

    private final int row;

    public Row(int row) {
        validateRow(row);
        this.row = row;
    }

    private void validateRow(int row) {
        if (row > MAX_ROW || row < MIN_ROW) {
            throw new IllegalArgumentException("장기판 행의 범위를 벗어났습니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Row row1 = (Row) o;
        return row == row1.row;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(row);
    }
}
