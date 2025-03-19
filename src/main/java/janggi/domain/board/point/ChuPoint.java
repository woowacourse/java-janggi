package janggi.domain.board.point;

import janggi.domain.board.Direction;

public record ChuPoint(
        int x,
        int y
) implements Point {
    @Override
    public Point move(Direction direction) {
        return switch (direction) {
            case UP -> new ChuPoint(x - 1, y);
            case DOWN -> new ChuPoint(x + 1, y);
            case LEFT -> new ChuPoint(x, y - 1);
            case RIGHT -> new ChuPoint(x, y + 1);
            case UP_LEFT_DIAGONAL -> new ChuPoint(x - 1, y - 1);
            case UP_RIGHT_DIAGONAL -> new ChuPoint(x - 1, y + 1);
            case DOWN_LEFT_DIAGONAL -> new ChuPoint(x + 1, y - 1);
            case DOWN_RIGHT_DIAGONAL -> new ChuPoint(x + 1, y + 1);
        };
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
        return new ChuPoint(endPoint.getX(), endPoint.getY());
    }

    @Override
    public boolean isOutOfBoundary() {
        return x > 10 || x < 1 || y > 9 || y < 1;
    }
}
