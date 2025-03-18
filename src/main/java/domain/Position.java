package domain;

import java.awt.Point;

public class Position {

    private final Point point;

    public Position(final int x, final int y) {
        this.point = new Point(x, y);
    }

    public int getX() {
        return point.x;
    }

    public int getY() {
        return point.y;
    }
    
}
