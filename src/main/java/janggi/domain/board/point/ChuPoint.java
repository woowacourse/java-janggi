package janggi.domain.board.point;

import janggi.domain.board.Direction;

public class ChuPoint extends Point {

    public ChuPoint(int x, int y) {
        super(x, y);
    }

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
}
