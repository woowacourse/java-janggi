package janggi.domain.board.coordinate;

public record Point(
        int x,
        int y
) {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;

    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;

    private static final Point[][] CACHE = initCache();

    private static Point[][] initCache() {
        Point[][] cache = new Point[MAX_X + 1][MAX_Y + 1];
        for (int x = MIN_X; x <= MAX_X; x++) {
            for (int y = MIN_Y; y <= MAX_Y; y++) {
                cache[x][y] = new Point(x, y);
            }
        }
        return cache;
    }

    public static Point of(int x, int y) {
        validateRange(x, y);
        return CACHE[x][y];
    }

    public static boolean isInRange(int nx, int ny) {
        return nx >= MIN_X && nx <= MAX_X && ny >= MIN_Y && ny <= MAX_Y;
    }

    private static void validateRange(int x, int y) {
        if (!isInRange(x, y)) {
            throw new IllegalStateException("좌표의 범위는 {%d,%d} ~ {%d,%d} 입니다.".formatted(MIN_X, MIN_Y, MAX_X, MAX_Y));
        }
    }

    public Point add(int dx, int dy) {
        return Point.of(this.x + dx, this.y + dy);
    }
}
