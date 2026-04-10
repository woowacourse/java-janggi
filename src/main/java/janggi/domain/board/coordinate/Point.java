package janggi.domain.board.coordinate;

import janggi.domain.board.Board;

public record Point(int x, int y) {

    private static final Point[][] CACHE = initCache();

    private static Point[][] initCache() {
        Point[][] cache = new Point[Board.X_SIZE + 1][Board.Y_SIZE + 1];
        for (int x = 0; x <= Board.X_SIZE; x++) {
            for (int y = 0; y <= Board.Y_SIZE; y++) {
                cache[x][y] = new Point(x, y);
            }
        }
        return cache;
    }

    public static Point of(int x, int y) {
        validateRange(x, y);
        return CACHE[x][y];
    }

    public Point add(int dx, int dy) {
        return Point.of(this.x + dx, this.y + dy);
    }

    private static void validateRange(int x, int y) {
        if (!Board.isInBoard(x, y)) {
            throw new IllegalStateException(
                    "좌표의 범위는 {%d,%d} ~ {%d,%d} 입니다.".formatted(0, 0, Board.X_SIZE, Board.Y_SIZE));
        }
    }
}
