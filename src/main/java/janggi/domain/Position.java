package janggi.domain;

import janggi.domain.movement.Direction;
import janggi.global.Pair;
import janggi.utils.Lists;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;

public final class Position {

    public static final int MAXIMUM_ROW = 10;
    public static final int MAXIMUM_COLUMN = 9;
    public static final int MINIMUM_ROW = 1;
    public static final int MINIMUM_COLUMN = 1;
    private static final int ROW_FLIP_VALUE = 11;
    private static final Map<Pair<Integer, Integer>, Position> CACHE;

    static {
        final List<Integer> rows = IntStream.rangeClosed(MINIMUM_ROW, MAXIMUM_ROW)
            .boxed().toList();
        final List<Integer> columns = IntStream.rangeClosed(MINIMUM_COLUMN, MAXIMUM_COLUMN)
            .boxed().toList();

        CACHE = new LinkedHashMap<>();
        Lists.cartesianProduct(rows, columns)
            .forEach(positionPair
                -> CACHE.put(positionPair,
                new Position(positionPair.left(), positionPair.right())));
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
        final Pair<Integer, Integer> positionPair = new Pair<>(row, column);
        if (!CACHE.containsKey(positionPair)) {
            CACHE.put(positionPair, new Position(row, column));
        }
        return CACHE.get(positionPair);
    }

    public static Position from(final Pair<Integer, Integer> rawPosition) {
        return valueOf(rawPosition.left(), rawPosition.right());
    }

    private static void validateRowRange(final int row) {
        if (row < MINIMUM_ROW || row > MAXIMUM_ROW) {
            throw new IllegalArgumentException(
                String.format("행 값은 %d ~ %d사이의 정수여야 합니다.", MINIMUM_ROW, MAXIMUM_ROW));
        }
    }

    private static void validateColumnRange(final int column) {
        if (column < MINIMUM_COLUMN || column > MAXIMUM_COLUMN) {
            throw new IllegalArgumentException(
                String.format("열 값은 %d ~ %d사이의 정수여야 합니다.", MINIMUM_COLUMN, MAXIMUM_COLUMN));
        }
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

    public Position flipAroundMiddleRow() {
        return Position.valueOf(ROW_FLIP_VALUE - row, column);
    }

    public boolean checkNextBound(final int distance, final Direction direction) {
        final int nextRow = row + direction.getRowDirection() * distance;
        final int nextColumn = column + direction.getColumnDirection() * distance;

        return nextRow >= MINIMUM_ROW && nextRow <= MAXIMUM_ROW && nextColumn >= MINIMUM_COLUMN
            && nextColumn <= MAXIMUM_COLUMN;
    }

    public Position calculateNext(final int distance, final Direction direction) {
        final int nextRow = row + direction.getRowDirection() * distance;
        final int nextColumn = column + direction.getColumnDirection() * distance;

        return Position.valueOf(Math.clamp(nextRow, MINIMUM_ROW, MAXIMUM_ROW),
            Math.clamp(nextColumn, MINIMUM_COLUMN, MAXIMUM_COLUMN));
    }
}
