package janggi.domain;

import java.util.List;
import java.util.Objects;

public class Position {
    public static final int POSITION_COMPONENTS_SIZE = 2;

    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    private static final String INVALID_POSITION_SIZE = "행과 열 두 개의 값만 입력하세요.";
    private static final String INVALID_ROW_RANGE = "유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.";
    private static final String INVALID_COL_RANGE = "유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.";

    private static final String INVALID_LINEAR_POSITION = "직선이 아닙니다.";

    private final int x;
    private final int y;

    public Position(int x, int y) {
        validate(x, y);
        this.x = x;
        this.y = y;
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
        return x == position.x && y == position.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public static Position from(List<Integer> inputs) {
            validateSize(inputs);
            int row = inputs.getFirst();
            int column = inputs.getLast();

            return new Position(row, column);
    }

    private static void validateSize(List<Integer> inputs) {
        if (inputs.size() != POSITION_COMPONENTS_SIZE) {
            throw new IllegalArgumentException(INVALID_POSITION_SIZE);
        }
    }

    private static void validate(int x, int y) {
        validateRow(x);
        validateCol(y);
    }

    private static void validateRow(int x) {
        if (x < BOARD_START_ROWS || x > BOARD_END_ROWS) {
            throw new IllegalArgumentException(INVALID_ROW_RANGE);
        }
    }

    private static void validateCol(int y) {
        if (y < BOARD_START_COLS || y > BOARD_END_COLS) {
            throw new IllegalArgumentException(INVALID_COL_RANGE);
        }
    }

    public Position move(Movement movement) {
        return new Position(x + movement.getDx(), y + movement.getDy());
    }

    public Movement getLinearDirection(Position position) {
        if(!isLinear(position)) {
            throw new IllegalStateException(INVALID_LINEAR_POSITION);
        }

        int dx = Integer.compare(position.x, x);
        int dy = Integer.compare(position.y, y);

        return Movement.of(dx, dy);
    }

    public int calculateLinearDistance(Position position) {
        if(!isLinear(position)) {
            throw new IllegalStateException(INVALID_LINEAR_POSITION);
        }

        int dx = Math.abs(position.x - x);
        int dy = Math.abs(position.y - y);

        return Math.max(dx, dy);
    }

    private boolean isLinear(Position position) {
        int dx = Math.abs(position.x - x);
        int dy = Math.abs(position.y - y);

        return (dx == 0 && dy != 0) || (dy == 0 && dx != 0);
    }
}
