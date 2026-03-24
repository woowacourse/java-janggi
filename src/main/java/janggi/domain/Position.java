package janggi.domain;

import java.util.LinkedHashMap;
import java.util.Map;

public final class Position {

    private static final int MAXIMUM_ROW = 10;
    private static final int MAXIMUM_COLUMN = 9;
    private static final int MINIMUM_ROW = 1;
    private static final int MINIMUM_COLUMN = 1;
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
        validateRowRange(row);
        validateColumnRange(column);
        this.row = row;
        this.column = column;
    }

    public static Position valueOf(final int row, final int column) {
        final Map<Integer, Position> secondaryMap = CACHE.get(row);
        if (!secondaryMap.containsKey(column)) {
            secondaryMap.put(column, new Position(row, column));
        }
        return secondaryMap.get(column);
    }

    private void validateRowRange(final int row) {
        if (row < MINIMUM_ROW || row > MAXIMUM_ROW) {
            throw new IllegalArgumentException("행 입력은 1~10을 입력해야 합니다.");
        }
    }

    private void validateColumnRange(final int column) {
        if (column < MINIMUM_COLUMN || column > MAXIMUM_COLUMN) {
            throw new IllegalArgumentException("열 입력은 1~9을 입력해야 합니다.");
        }
    }
}
