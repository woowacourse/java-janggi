package janggi.domain.board;

public record Point(int x, int y) {
    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_COLUMN = 9;
    private static final int MINIMUM_COLUMN = 1;
    
    public Point move(Direction direction) {
        return new Point(x + direction.getX(), y + direction.getY());
    }

    public boolean isSamePosition(Point point) {
        return this.x == point.x && y == point.y;
    }

    public boolean isNotOutOfBoundary() {
        return x <= MAXIMUM_ROW && x >= MINIMUM_ROW && y <= MAXIMUM_COLUMN && y >= MINIMUM_COLUMN;
    }
}
