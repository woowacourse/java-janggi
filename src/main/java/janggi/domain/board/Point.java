package janggi.domain.board;

public record Point(int x, int y) {
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
