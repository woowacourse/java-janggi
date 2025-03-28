package janggi.domain.piece;

import java.util.Set;

public record Point(int x, int y) {
    private static final Set<Point> palace = Set.of(
            new Point(1, 4), new Point(1, 5), new Point(1, 6),
            new Point(2, 4), new Point(2, 5), new Point(2, 6),
            new Point(3, 4), new Point(3, 5), new Point(3, 6),

            new Point(8, 4), new Point(8, 5), new Point(8, 6),
            new Point(9, 4), new Point(9, 5), new Point(9, 6),
            new Point(10, 4), new Point(10, 5), new Point(10, 6)
    );

    private static final Set<Point> palace2 = Set.of(
            new Point(1, 4), new Point(1, 6),
            new Point(2, 5),
            new Point(3, 4), new Point(3, 6),

            new Point(8, 4), new Point(8, 6),
            new Point(9, 5),
            new Point(10, 4), new Point(10, 6)
    );
    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_COLUMN = 1;
    private static final int MAXIMUM_COLUMN = 9;

    public Point {
        if (isOutOfBoundary(x, y)) {
            throw new IllegalArgumentException("범위를 벗어났습니다.");
        }
    }

    public Point move(Direction direction) {
        return new Point(x + direction.getX(), y + direction.getY());
    }

    public boolean canMove(Direction direction) {
        int nextX = x + direction.getX();
        int nextY = y + direction.getY();
        return nextX >= MINIMUM_ROW && nextX <= MAXIMUM_ROW && nextY >= MINIMUM_COLUMN && nextY <= MAXIMUM_COLUMN;
    }

    public boolean isOutOfBoundary() {
        return isOutOfBoundary(x, y);
    }

    private boolean isOutOfBoundary(int x, int y) {
        return x > MAXIMUM_ROW || x < MINIMUM_ROW || y > MAXIMUM_COLUMN || y < MINIMUM_COLUMN;
    }
}
