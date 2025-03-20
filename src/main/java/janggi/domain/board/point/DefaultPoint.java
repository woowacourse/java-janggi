package janggi.domain.board.point;

import janggi.domain.board.Direction;

public class DefaultPoint extends Point {

    public DefaultPoint(int x, int y) {
        super(x, y);
    }

    @Override
    public Point move(Direction direction) {
        throw new IllegalStateException("움직일 수 없습니다.");
    }
}
