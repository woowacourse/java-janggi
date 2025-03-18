package domain;

import java.awt.Point;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Position position)) {
            return false;
        }
        return Objects.equals(point, position.point);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(point);
    }
}
