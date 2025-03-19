package janggi.domain.board.point;

import janggi.domain.board.Direction;

public record DefaultPoint(
        int x,
        int y
) implements Point {

    @Override
    public Point move(Direction direction) {
        throw new IllegalStateException("움직일 수 없습니다.");
    }

    @Override
    public boolean isSamePosition(Point point) {
        return this.x == point.getX() && y == point.getY();
    }

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public Point copy(Point endPoint) {
        return new DefaultPoint(endPoint.getX(), endPoint.getY());
    }

    @Override
    public boolean isOutOfBoundary() {
        return x > 10 || x < 1 || y > 9 || y < 1;
    }
}
