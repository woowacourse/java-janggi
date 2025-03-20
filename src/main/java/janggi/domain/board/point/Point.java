package janggi.domain.board.point;

import janggi.domain.board.Direction;
import java.util.Objects;

public abstract class Point {
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
        return x <= 10 && x >= 1 && y <= 9 && y >= 1;
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
