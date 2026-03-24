package janggi.domain.vo;

import java.util.Objects;

public class Position {
    private static final int MIN_ROW = 0;
    private static final int MAX_ROW = 9;
    private static final int MIN_COL = 0;
    private static final int MAX_COL = 8;

    private final int row;
    private final int col;

    public Position(int row, int col) {
        validateRange(row, col);

        this.row = row;
        this.col = col;
    }

    private void validateRange(int row, int col) {
        if (row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("범위 밖의 행입니다.");
        }

        if (col < MIN_COL || col > MAX_COL) {
            throw new IllegalArgumentException("범위 밖의 열입니다.");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return row == position.row && col == position.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
