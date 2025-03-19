package janggi;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position {

    private static final int MAX_ROW = 10;
    private static final int MAX_COLUMN = 9;

    private static final Map<Integer, Map<Integer, Position>> CACHE;

    static {
        CACHE = new HashMap<>();
        for (int row = 1; row <= MAX_ROW; row++) {
            CACHE.put(row, new HashMap<>());
            for (int column = 1; column <= MAX_COLUMN; column++) {
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

    public static Position of(int row, int column) {
        validateRange(row, column);
        return CACHE.get(row).get(column);
    }

    private static void validateRange(int row, int column) {
        if (1 > row || row > MAX_ROW || 1 > column || column > MAX_COLUMN) {
            throw new IllegalArgumentException("[ERROR] 유효하지 않은 위치입니다.");
        }
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
