package janggi.fixture;

import janggi.board.Position;

public class PositionFixture {

    public static final Position CENTER = createPosition(5, 5);
    private static final int MIN_COLUMN = 0;
    private static final int MAX_COLUMN = 8;
    private static final int MIN_ROW = 0;
    private static final int MAX_ROW = 9;

    public static Position createPosition(int column, int row) {
        if (column < MIN_COLUMN || column > MAX_COLUMN || row < MIN_ROW || row > MAX_ROW) {
            throw new IllegalArgumentException("유효하지 않은 좌표값입니다. 열(1-9), 행(1-10)");
        }
        return new Position(column, row);
    }
}
