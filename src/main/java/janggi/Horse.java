package janggi;

public class Horse {

    private final Camp camp;

    public Horse(Camp camp) {
        this.camp = camp;
    }

    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 1) ||
                (fromPoint.calculateXDistance(toPoint) == 1 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("마는 직선으로 한 칸, 대각선으로 한 칸 움직여야 합니다.");
        }
    }
}
