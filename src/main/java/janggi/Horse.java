package janggi;

public final class Horse extends Piece {

    public Horse(Camp camp) {
        super(camp);
    }

    @Override
    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 1) ||
                (fromPoint.calculateXDistance(toPoint) == 1 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }
}
