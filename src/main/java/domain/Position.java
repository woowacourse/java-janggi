package domain;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Position {

    public static int MAX_COL_VALUE = 8;
    public static int MIN_COL_VALUE = 0;
    public static int MAX_ROW_VALUE = 9;
    public static int MIN_ROW_VALUE = 0;

    private static final List<Position> DIAGONAL_POINTS = List.of(new Position(3, 0),
            new Position(5, 0), new Position(4, 1), new Position(3, 2), new Position(5, 2),
            new Position(3, 7), new Position(5, 7), new Position(4, 8), new Position(3, 9),
            new Position(5, 9));
    public static int PALACE_MAX_COL_VALUE = 5;
    public static int PALACE_MIN_COL_VALUE = 3;
    public static int PALACE_MAX_ROW_VALUE_HAN = 2;
    public static int PALACE_MIN_ROW_VALUE_HAN = 0;
    public static int PALACE_MAX_ROW_VALUE_CHO = 9;
    public static int PALACE_MIN_ROW_VALUE_CHO = 7;
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

    public boolean isInPalace() {
        return isInPalaceCol() && isInPalaceRow();
    }

    private boolean isInPalaceCol() {
        return col <= PALACE_MAX_COL_VALUE && col >= PALACE_MIN_COL_VALUE;
    }

    public boolean isPalaceDiagonalPosition() {
        return DIAGONAL_POINTS.contains(this);
    }

    private boolean isInPalaceRow() {
        return row <= PALACE_MAX_ROW_VALUE_HAN && row >= PALACE_MIN_ROW_VALUE_HAN
                || row <= PALACE_MAX_ROW_VALUE_CHO && row >= PALACE_MIN_ROW_VALUE_CHO;
    }

    public int getCol() {
        return col;
    }

    public int getRow() {
        return row;
    }

    private void validatePosCol(int col) {
        if (col < MIN_COL_VALUE || col > MAX_COL_VALUE) {
            throw new IllegalArgumentException("[ERROR] col 좌표는 0 ~ 8 사이어야합니다.");
        }
    }

    private void validatePosRow(int row) {
        if (row < MIN_ROW_VALUE || row > MAX_ROW_VALUE) {
            throw new IllegalArgumentException("[ERROR] row 좌표는 0 ~ 9 사이어야합니다.");
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
