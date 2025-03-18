package janggi;

public final class Chariot extends Piece {

    public Chariot(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!(fromPoint.isHorizontal(toPoint) || fromPoint.isVertical(toPoint))) {
            throw new IllegalArgumentException("차는 상하좌우로 움직여야 합니다.");
        }
    }
}
