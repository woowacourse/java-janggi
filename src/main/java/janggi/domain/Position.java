package janggi.domain;

import java.util.List;

import static janggi.domain.board.Board.BOARD_END_COLS;
import static janggi.domain.board.Board.BOARD_END_ROWS;
import static janggi.domain.board.Board.BOARD_START_COLS;
import static janggi.domain.board.Board.BOARD_START_ROWS;

public record Position(int x, int y) {
    private static final String INVALID_POSITION_SIZE = "행과 열 두 개의 값만 입력하세요.";
    private static final String INVALID_ROW_RANGE = "유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.";
    private static final String INVALID_COL_RANGE = "유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.";

    public static Position from(List<Integer> inputs) {
        int r = inputs.getFirst();
        int c = inputs.getLast();
        validate(r, c, inputs);
        return new Position(r, c);
    }

    private static void validate(int x, int y, List<Integer> inputs) {
        validateSize(inputs);
        validateRow(x);
        validateCol(y);
    }

    private static void validateSize(List<Integer> inputs) {
        if (inputs.size() != 2) {
            throw new IllegalArgumentException(INVALID_POSITION_SIZE);
        }
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

    public int calculateDistance(Position position, boolean isVertical) {
        if (isVertical) {
            return position.x - this.x;
        }
        return position.y - this.y;
    }

    public boolean isHorizon(Position position) {
        return position.x == this.x;
    }

    public boolean isVertical(Position position) {
        return position.y == this.y;
    }

    public boolean isAbove(Position position) {
        return position.x < x;
    }

    public boolean isBelow(Position position) {
        return position.x > x;
    }
}
