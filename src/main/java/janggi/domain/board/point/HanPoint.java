package janggi.domain.board.point;

import janggi.domain.board.Direction;

public class HanPoint extends Point {

    public HanPoint(int x, int y) {
        super(x, y);
    }

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
}
