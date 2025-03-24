package janggi.domain.board;

public enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    RIGHT(0, +1),
    UP_RIGHT(-1, +1),
    LEFT(0, -1),
    UP_LEFT(-1, -1),
    DOWN_RIGHT(1, 1),
    DOWN_LEFT(1, -1);

    private final int x;
    private final int y;

    Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point moveFrom(Point start) {
        return new Point(this.x + start.x(), this.y + start.y());
    }

    public boolean canMoveFrom(Point start) {
        try {
            moveFrom(start);
        } catch (IllegalStateException ex) {
            return false;
        }
        return true;
    }
}
