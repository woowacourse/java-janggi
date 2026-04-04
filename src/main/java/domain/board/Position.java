package domain.board;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Position {
    private static final int BOARD_MIN_INDEX = 0;
    private static final int BOARD_MAX_ROW = 9;
    private static final int BOARD_MAX_COLUMN = 8;
    private static final int BOARD_COLUMN_SIZE = BOARD_MAX_COLUMN + 1;
    private static final Map<Integer, Position> CACHE = createCache();

    private final Row row;

    private final Column column;
    private Position(Row row, Column column) {
        this.row = row;
        this.column = column;
    }

    public Position next(Direction direction) {
        return direction.move(this);
    }

    public static Position of(int row, int column) {
        validateBoardIndex(row, column);
        return CACHE.get(toCacheKey(row, column));
    }

    public int row() {
        return row.value();
    }

    public int column() {
        return column.value();
    }

    public boolean isInsideBoard() {
        return row() >= BOARD_MIN_INDEX && row() <= BOARD_MAX_ROW
                && column() >= BOARD_MIN_INDEX && column() <= BOARD_MAX_COLUMN;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Position position = (Position) o;
        return Objects.equals(row, position.row) && Objects.equals(column, position.column);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }

    @Override
    public String toString() {
        return "(" + row() + "," + column() + ")";
    }

    private static Map<Integer, Position> createCache() {
        final Map<Integer, Position> cache = new HashMap<>();
        for (int row = BOARD_MIN_INDEX; row <= BOARD_MAX_ROW; row++) {
            for (int column = BOARD_MIN_INDEX; column <= BOARD_MAX_COLUMN; column++) {
                cache.put(toCacheKey(row, column), new Position(new Row(row), new Column(column)));
            }
        }
        return cache;
    }

    private static int toCacheKey(int row, int column) {
        return row * BOARD_COLUMN_SIZE + column;
    }

    private static void validateBoardIndex(int row, int column) {
        if (row < BOARD_MIN_INDEX) {
            throw new IllegalArgumentException("행의 최소 값은 " + BOARD_MIN_INDEX + "입니다.");
        }
        if (row > BOARD_MAX_ROW) {
            throw new IllegalArgumentException("행의 최대 값은 " + BOARD_MAX_ROW + "입니다.");
        }
        if (column < BOARD_MIN_INDEX) {
            throw new IllegalArgumentException("열의 최소 값은 " + BOARD_MIN_INDEX + "입니다.");
        }
        if (column > BOARD_MAX_COLUMN) {
            throw new IllegalArgumentException("열의 최대 값은 " + BOARD_MAX_COLUMN + "입니다.");
        }
    }

}



