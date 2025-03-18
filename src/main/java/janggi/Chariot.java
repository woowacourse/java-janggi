package janggi;

public class Chariot {

    private final Camp camp;

    public Chariot(Camp camp) {
        this.camp = camp;
    }

    public void validateMove(Point fromPoint, Point toPoint) {
        if (!(fromPoint.isHorizontal(toPoint) || fromPoint.isVertical(toPoint))) {
            throw new IllegalArgumentException("차는 상하좌우로 움직여야 합니다.");
        }
    }
}
