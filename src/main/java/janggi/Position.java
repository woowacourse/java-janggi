package janggi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position {

    private static final int MIN_ROW = 1;
    private static final int MIN_COLUMN = 1;
    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    private static final Map<Integer, Map<Integer, Position>> CACHE;

    static {
        CACHE = new HashMap<>();
        for (int row = MIN_ROW; row <= MAX_ROW; row++) {
            CACHE.put(row, new HashMap<>());
            for (int column = MIN_COLUMN; column <= MAX_COLUMN; column++) {
                CACHE.get(row).put(column, new Position(row, column));
            }
        }
    }

    private final int row;
    private final int column;

    private Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public static Position of(final int row, final int column) {
        validateRange(row, column);
        return CACHE.get(row).get(column);
    }

    private static void validateRange(final int row, final int column) {
        if (MIN_ROW > row || row > MAX_ROW || MIN_COLUMN > column || column > MAX_COLUMN) {
            throw new IllegalArgumentException("유효하지 않은 위치입니다.");
        }
    }

    public Position adjust(final int rowDirection, final int columnDirection) {
        return Position.of(this.row + rowDirection, this.column + columnDirection);
    }

    public int subtractRow(final Position other) {
        return this.row - other.row;
    }

    public int subtractColumn(final Position other) {
        return this.column - other.column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Position position = (Position) object;
        return getRow() == position.getRow() && getColumn() == position.getColumn();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}
