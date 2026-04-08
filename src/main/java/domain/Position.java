package domain;

import java.util.Objects;
import java.util.Optional;

public class Position {

    public static int MAX_COL_VALUE = 8;
    public static int MIN_COL_VALUE = 0;
    public static int MAX_ROW_VALUE = 9;
    public static int MIN_ROW_VALUE = 0;

    private final int col;
    private final int row;

    public Position(int col, int row) {
        validatePosCol(col);
        validatePosRow(row);
        this.col = col;
        this.row = row;
    }

    public static Optional<Position> of(int col, int row) {
        if (col < MIN_COL_VALUE || col > MAX_COL_VALUE || row < MIN_ROW_VALUE
                || row > MAX_ROW_VALUE) {
            return Optional.empty();
        }
        return Optional.of(new Position(col, row));
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    private void validatePosCol(int col) {
        if (col < MIN_COL_VALUE || col > MAX_COL_VALUE) {
            throw new IllegalArgumentException(
                    "[ERROR] col 좌표는 0 ~ 8 사이어야합니다.");
        }
    }

    private void validatePosRow(int row) {
        if (row < MIN_ROW_VALUE || row > MAX_ROW_VALUE) {
            throw new IllegalArgumentException(
                    "[ERROR] row 좌표는 0 ~ 9 사이어야합니다.");
        }
    }

    public Position reverseRow() {
        return new Position(this.col, MAX_ROW_VALUE - this.row);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Position position = (Position) o;
        return col == position.col && row == position.row;
    }

    @Override
    public int hashCode() {
        return Objects.hash(col, row);
    }
}
