package domain;

public class Point {
    private static final int MAX_X = 8;
    private static final int MAX_Y = 9;
    private final int x;
    private final int y;

    private Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public static Point of(final String x, final String y) {
        final int parsedX = parseInt(x);
        final int parsedY = parseInt(y);
        validateRange(parsedX, MAX_X);
        validateRange(parsedY, MAX_Y);
        return new Point(parsedX, parsedY);
    }

    public static Point of(final int x, final int y) {
        return new Point(x, y);
    }

    private static void validateRange(final int point, final int maxPoint) {
        if (point < 0 || point > maxPoint) {
            throw new IllegalArgumentException("X축은 0부터 8까지, Y축은 0부터 9까지 입력 가능합니다.");
        }
    }

    private static int parseInt(final String x) {
        try {
            return Integer.parseInt(x);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("숫자만 입력 가능합니다.");
        }
    }
}
