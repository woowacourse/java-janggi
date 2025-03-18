package domain;

import java.awt.Point;

public class Position {

    private final Point point;

    public Position(final int x, final int y) {
        if (x < 0 || y < 0 || x > 9 || y > 8) {
            throw new IllegalArgumentException("위치는 장기판 내부여야 합니다.");
        }
        this.point = new Point(x, y);
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }

}
