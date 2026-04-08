package janggi.domain;

import janggi.domain.movement.Direction;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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

    private static void validateRowRange(final int row) {
        if (row < MINIMUM_ROW || row > MAXIMUM_ROW) {
            throw new IllegalArgumentException(String.format("행 입력은 %d~%d을 입력해야 합니다.", MINIMUM_ROW, MAXIMUM_ROW));
        }
    }

    private static void validateColumnRange(final int column) {
        if (column < MINIMUM_COLUMN || column > MAXIMUM_COLUMN) {
            throw new IllegalArgumentException(String.format("열 입력은 %d~%d을 입력해야 합니다.", MINIMUM_COLUMN, MAXIMUM_COLUMN));
        }
    }

    public Position flipAroundMiddleRow() {
        return Position.valueOf(ROW_FLIP_VALUE - row, column);
    }

    public boolean checkNextBound(final Direction direction) {
        final int nextRow = row + direction.getRowDirection();
        final int nextColumn = column + direction.getColumnDirection();

        return nextRow >= MINIMUM_ROW && nextRow <= MAXIMUM_ROW && nextColumn >= MINIMUM_COLUMN
                && nextColumn <= MAXIMUM_COLUMN;
    }

    public boolean checkNextBound(final Direction direction, final int distance) {
        final int nextRow = row + direction.getRowDirection() * distance;
        final int nextColumn = column + direction.getColumnDirection() * distance;

        return nextRow >= MINIMUM_ROW && nextRow <= MAXIMUM_ROW && nextColumn >= MINIMUM_COLUMN
                && nextColumn <= MAXIMUM_COLUMN;
    }

    public Position calculateNext(final Direction direction) {
        final int nextRow = row + direction.getRowDirection();
        final int nextColumn = column + direction.getColumnDirection();

        return Position.valueOf(Math.clamp(nextRow, MINIMUM_ROW, MAXIMUM_ROW),
                Math.clamp(nextColumn, MINIMUM_COLUMN, MAXIMUM_COLUMN));
    }

    public Position calculateNext(final Direction direction, final int distance) {
        final int nextRow = row + direction.getRowDirection() * distance;
        final int nextColumn = column + direction.getColumnDirection() * distance;

        return Position.valueOf(Math.clamp(nextRow, MINIMUM_ROW, MAXIMUM_ROW),
                Math.clamp(nextColumn, MINIMUM_COLUMN, MAXIMUM_COLUMN));
    }

    @Override
    public boolean equals(final Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        final Position position = (Position) object;
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
