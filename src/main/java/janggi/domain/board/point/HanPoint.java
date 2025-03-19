package janggi.domain.board.point;

import janggi.domain.board.Direction;

public record HanPoint(
        int x,
        int y
) implements Point {

    @Override
    public Point move(Direction direction) {
        return switch (direction) {
            case UP -> new HanPoint(x + 1, y);
            case DOWN -> new HanPoint(x - 1, y);
            case LEFT -> new HanPoint(x, y + 1);
            case RIGHT -> new HanPoint(x, y - 1);
            case UP_LEFT_DIAGONAL -> new HanPoint(x + 1, y + 1);
            case UP_RIGHT_DIAGONAL -> new HanPoint(x + 1, y - 1);
            case DOWN_LEFT_DIAGONAL -> new HanPoint(x - 1, y + 1);
            case DOWN_RIGHT_DIAGONAL -> new HanPoint(x - 1, y - 1);
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
        return new HanPoint(endPoint.getX(), endPoint.getY());
    }

    @Override
    public boolean isOutOfBoundary() {
        return x > 10 || x < 1 || y > 9 || y < 1;
    }
}
