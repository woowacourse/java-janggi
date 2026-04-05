package janggi.domain;

import static janggi.domain.Movement.INVALID_DELTA_DIRECTION_MESSAGE;

import java.util.List;
import java.util.Objects;

public class Position {
    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    public static final int POSITION_COMPONENTS_SIZE = 2;

    private static final String INVALID_POSITION_SIZE = "행과 열 두 개의 값만 입력하세요.";
    private static final String INVALID_ROW_RANGE = "유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.";
    private static final String INVALID_COL_RANGE = "유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.";

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

    public boolean isDiagonal(Position target) {
        int dx = Math.abs(target.x - x);
        int dy = Math.abs(target.y - y);

        return dx == dy && dx != 0;
    }

    public Movement calculateDirection(Position destination) {
        int dx = Math.abs(destination.x - x);
        int dy = Math.abs(destination.y - y);

        if(dx == 0 || dy == 0 || dx == dy) {
            return Movement.of(
                    Integer.compare(destination.x, x),
                    Integer.compare(destination.y, y)
            );
        }

        throw new IllegalStateException(INVALID_DELTA_DIRECTION_MESSAGE);
    }

    public int calculateLinearDistance(Position destination) {
        return Math.max(Math.abs(destination.x - x), Math.abs(destination.y - y));
    }

    public boolean isRange(int startX, int endX, int startY, int endY) {
        return x >= startX && x <= endX && y >= startY && y <= endY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
