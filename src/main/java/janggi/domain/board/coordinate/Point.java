package janggi.domain.board.coordinate;

public record Point(
        int x,
        int y
) {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;

    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;

    public Point {
        validateRange(x, y);
    }

    public static boolean isInRange(int nx, int ny) {
        return nx >= MIN_X && nx <= MAX_X && ny >= MIN_Y && ny <= MAX_Y;
    }

    private void validateRange(int x, int y) {
        if (!isInRange(x, y)) {
            throw new IllegalStateException("좌표의 범위는 {%d,%d} ~ {%d,%d} 입니다.".formatted(MIN_X, MIN_Y, MAX_X, MAX_Y));
        }
    }

    public Point add(int x, int y) {
        return new Point(x + this.x, y + this.y);
    }
}
