package janggi;

public class Byeong {

    public void validateMove(Point fromPoint, Point toPoint) {
        if (fromPoint.getY() < toPoint.getY()) {
            throw new IllegalArgumentException("병은 뒤로 갈 수 없습니다.");
        }
        if (Math.abs(toPoint.getY() - fromPoint.getY() + fromPoint.getX() - toPoint.getX()) != 1) {
            throw new IllegalArgumentException("병은 앞 또는 양 옆으로 한 칸만 움직일 수 있습니다.");
        }
    }
}
