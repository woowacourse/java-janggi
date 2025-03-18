package janggi.piece;

import janggi.Camp;
import janggi.Point;

public final class Cannon extends Piece {

    public Cannon(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!(fromPoint.isHorizontal(toPoint) || fromPoint.isVertical(toPoint))) {
            throw new IllegalArgumentException("포는 상하좌우로 움직여야 합니다.");
        }
    }
}
