package domain;

import java.util.Objects;

public final class Coordinate {
    public static final int ROW_SIZE = 10;
    public static final int COL_SIZE = 9;
    public static final int BOARD_MIN_SIZE = 1;

    private final int row;
    private final int col;

    public Coordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public Coordinate move(int increaseRow, int increaseCol) {
        return new Coordinate(row + increaseRow, col + increaseCol);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Coordinate that = (Coordinate) o;
        return row == that.row && col == that.col;
    }

    public int hashCode() {
        return Objects.hash(row, col);
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
