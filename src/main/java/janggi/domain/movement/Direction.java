package janggi.domain.movement;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public final class Direction {

    private static final int MINIMUM_ROW_DIRECTION = -1;
    private static final int MAXIMUM_ROW_DIRECTION = 1;
    private static final int MINIMUM_COLUMN_DIRECTION = -1;
    private static final int MAXIMUM_COLUMN_DIRECTION = 1;
    private static final Map<Integer, Map<Integer, Direction>> CACHE;

    static {
        CACHE = new LinkedHashMap<>();
        IntStream.range(MINIMUM_COLUMN_DIRECTION, MAXIMUM_COLUMN_DIRECTION + 1)
            .forEach(rowDirection -> CACHE.put(rowDirection, new LinkedHashMap<>()));
    }

    private final int rowDirection;
    private final int columnDirection;

    private Direction(final int rowDirection, final int columnDirection) {
        this.rowDirection = rowDirection;
        this.columnDirection = columnDirection;
    }

    public static Direction valueOf(final int rowDirection, final int columnDirection) {
        validateRowDirectionRange(rowDirection);
        validateColumnDirectionRange(columnDirection);
        final Map<Integer, Direction> secondaryMap = CACHE.get(rowDirection);
        if (!secondaryMap.containsKey(columnDirection)) {
            secondaryMap.put(columnDirection, new Direction(rowDirection, columnDirection));
        }

        return secondaryMap.get(columnDirection);
    }

    private static void validateRowDirectionRange(final int rowDirection) {
        if (rowDirection < MINIMUM_ROW_DIRECTION || rowDirection > MAXIMUM_ROW_DIRECTION) {
            throw new IllegalArgumentException(String.format(
                "행 방향 값은 %d ~ %d 사이의 정수 값이어야 합니다.", MINIMUM_ROW_DIRECTION, MAXIMUM_ROW_DIRECTION));
        }
    }

    private static void validateColumnDirectionRange(final int columnDirection) {
        if (columnDirection < MINIMUM_COLUMN_DIRECTION
            || columnDirection > MAXIMUM_COLUMN_DIRECTION) {
            throw new IllegalArgumentException(String.format(
                "행 방향 값은 %d ~ %d 사이의 정수 값이어야 합니다.", MINIMUM_COLUMN_DIRECTION,
                MAXIMUM_COLUMN_DIRECTION));
        }
    }

    public int getRowDirection() {
        return rowDirection;
    }

    public int getColumnDirection() {
        return columnDirection;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Direction direction = (Direction) o;
        return rowDirection == direction.rowDirection
            && columnDirection == direction.columnDirection;
    }

    @Override
    public int hashCode() {
        return Objects.hash(rowDirection, columnDirection);
    }
}
