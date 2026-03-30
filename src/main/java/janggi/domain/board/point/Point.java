package janggi.domain.board.point;

import java.util.Objects;
import java.util.stream.IntStream;

public final class Point {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;

    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;

    private static final Point[][] INSTANCE = new Point[MAX_X + 1][MAX_Y + 1];

    static {
        IntStream.rangeClosed(Point.MIN_X, Point.MAX_X)
                .forEach(Point::InitInstance);
    }

    private final int x;
    private final int y;

    private Point(int x, int y) {
        validateRange(x, y);
        this.x = x;
        this.y = y;
    }

    public static Point of(int x, int y) {
        return INSTANCE[x][y];
    }

    private static void InitInstance(int x) {
        IntStream.rangeClosed(MIN_Y, MAX_Y)
                .forEach(y -> {
                    INSTANCE[x][y] = new Point(x, y);
                });

    }

    public static boolean isInRange(int nx, int ny) {
        return nx >= MIN_X && nx <= MAX_X && ny >= MIN_Y && ny <= MAX_Y;
    }

    private void validateRange(int x, int y) {
        if (!isInRange(x, y)) {
            throw new IllegalStateException("좌표의 범위는 {0,0} ~ {8,9} 입니다.");
        }
    }

    public Point add(int x, int y) {
        return Point.of(x + this.x, y + this.y);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        var that = (Point) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public String toString() {
        return "Point[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

}
