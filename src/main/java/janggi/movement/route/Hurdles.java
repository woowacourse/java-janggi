package janggi.movement.route;

import janggi.piece.Movable;
import janggi.point.Point;
import java.util.Map;

public class Hurdles {
    private final Map<Point, Movable> hurdles;

    public Hurdles(Map<Point, Movable> hurdles) {
        this.hurdles = hurdles;
    }

    public boolean containsPoint(Point point) {
        return hurdles.containsKey(point);
    }

    public Movable findByPoint(Point point) {
        if (containsPoint(point)) {
            return hurdles.get(point);
        }
        throw new IllegalStateException();
    }
}
