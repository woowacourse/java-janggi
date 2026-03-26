package janggi.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Position {

    public static final int MAXIMUM_ROW = 10;
    public static final int MAXIMUM_COLUMN = 9;
    public static final int MINIMUM_ROW = 1;
    public static final int MINIMUM_COLUMN = 1;
    private static final int ROW_FLIP_VALUE = 11;
    private static final Map<Integer, Map<Integer, Position>> CACHE;

    static {
        CACHE = new LinkedHashMap<>();
        for (int row = MINIMUM_ROW; row <= MAXIMUM_ROW; row++) {
            CACHE.put(row, new LinkedHashMap<>());
        }
    }

    private final int row;
    private final int column;

    private Position(final int row, final int column) {
        this.row = row;
        this.column = column;
    }

    public static Position valueOf(final int row, final int column) {
        validateRowRange(row);
        validateColumnRange(column);
        final Map<Integer, Position> secondaryMap = CACHE.get(row);
        if (!secondaryMap.containsKey(column)) {
            secondaryMap.put(column, new Position(row, column));
        }
        return secondaryMap.get(column);
    }

    public Position flipAroundMiddleRow() {
        return Position.valueOf(ROW_FLIP_VALUE - row, column);
    }

    private static void validateRowRange(final int row) {
        if (row < MINIMUM_ROW || row > MAXIMUM_ROW) {
            throw new IllegalArgumentException("행 입력은 1~10을 입력해야 합니다.");
        }
    }

    private static void validateColumnRange(final int column) {
        if (column < MINIMUM_COLUMN || column > MAXIMUM_COLUMN) {
            throw new IllegalArgumentException("열 입력은 1~9을 입력해야 합니다.");
        }
    }
}
