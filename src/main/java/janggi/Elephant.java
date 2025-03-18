package janggi;

public class Elephant {

    private final Camp camp;

    public Elephant(Camp camp) {
        this.camp = camp;
    }

    public void validateMove(Point fromPoint, Point toPoint) {
        if (!((fromPoint.calculateXDistance(toPoint) == 2 && fromPoint.calculateYDistance(toPoint) == 3) ||
                (fromPoint.calculateXDistance(toPoint) == 3 && fromPoint.calculateYDistance(toPoint) == 2))) {
            throw new IllegalArgumentException("상은 직선으로 한 칸, 대각선으로 두 칸 움직여야 합니다.");
        }
    }
}
