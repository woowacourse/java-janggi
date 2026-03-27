package janggi.domain;

import java.util.List;

public record Position(int x, int y) {
    public static final int BOARD_START_ROWS = 1;
    public static final int BOARD_START_COLS = 1;
    public static final int BOARD_END_ROWS = 10;
    public static final int BOARD_END_COLS = 9;

    public static Position from(List<String> inputs) {
        try {
            List<Integer> parsedInputs = getParsedInputs(inputs);
            int r = parsedInputs.getFirst();
            int c = parsedInputs.getLast();

            validate(r, c);
            return new Position(r, c);
        } catch(Exception e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }

    private static List<Integer> getParsedInputs(List<String> inputs) {
        List<Integer> parsedInputs = inputs.stream().map(Integer::parseInt).toList();
        if (parsedInputs.size() != 2) {
            throw new IllegalArgumentException("행과 열 두 개의 값이 필요합니다.");
        }
        return parsedInputs;
    }

    private static void validate(int x, int y) {
        validateRow(x);
        validateCol(y);
    }

    private static void validateRow(int x) {
        if(x < BOARD_START_ROWS || x > BOARD_END_ROWS){
            throw new IllegalArgumentException("유효하지 않은 위치입니다. 행은 1부터 10까지 가능합니다.");
        }
    }

    private static void validateCol(int y) {
        if(y < BOARD_START_COLS || y > BOARD_END_COLS) {
            throw new IllegalArgumentException("유효하지 않은 위치입니다. 열은 1부터 9까지 가능합니다.");
        }
    }

    public Position move(Movement movement) {
        return new Position(x + movement.getDx(), y + movement.getDy());
    }

    public int calculateDistance(Position position, boolean isVertical) {
        if(isVertical){
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
}
