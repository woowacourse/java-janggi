package domain.piece;

import java.util.Objects;

public class Position {

    public static final int MIN_ROW = 1;
    public static final int MIN_COLUMN = 1;
    public static final int MAX_ROW = 9;
    public static final int MAX_COLUMN = 10;

    private final int row;
    private final int column;

    private Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(final int row, final int column) {
        validateRange(row, column);
        return new Position(row, column);
    }

    public static Position ofDirection(final int row, final int column) {
        return new Position(row, column);
    }

    private static void validateRange(final int row, final int column) {
        if (row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 좌표 입력은 9X10 보드 이내만 가능합니다.");
        }
    }

    public Position merge(Position other) {
        return new Position(this.row + other.row, this.column + other.column);
    }

    public boolean isValid() {
        return !(row < MIN_ROW || column < MIN_COLUMN || row > MAX_ROW || column > MAX_COLUMN);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return row == position.row && column == position.column;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }
}
