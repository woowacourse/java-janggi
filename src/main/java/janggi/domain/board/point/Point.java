package janggi.domain.board.point;

import janggi.domain.board.Direction;
import java.util.Objects;

public abstract class Point {
    private static final int MAXIMUM_ROW = 10;
    private static final int MINIMUM_ROW = 1;
    private static final int MAXIMUM_COLUMN = 9;
    private static final int MINIMUM_COLUMN = 1;

    protected final int x;
    protected final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public abstract Point move(Direction direction);

    public boolean isSamePosition(Point point) {
        return this.x == point.getX() && y == point.getY();
    }

    public Point copy(Point endPoint) {
        return new HanPoint(endPoint.getX(), endPoint.getY());
    }

    public boolean isNotOutOfBoundary() {
        return x <= MAXIMUM_ROW && x >= MINIMUM_ROW && y <= MAXIMUM_COLUMN && y >= MINIMUM_COLUMN;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Point point)) {
            return false;
        }
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
