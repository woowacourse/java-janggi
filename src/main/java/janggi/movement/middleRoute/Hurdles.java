package janggi.movement.middleRoute;

import janggi.piece.Piece;
import janggi.point.Point;
import java.util.Map;

public class Hurdles {
    private final Map<Point, Piece> hurdles;

    public Hurdles(Map<Point, Piece> hurdles) {
        this.hurdles = hurdles;
    }

    public boolean containsPoint(Point point) {
        return hurdles.containsKey(point);
    }

    public Piece findByPoint(Point point) {
        if (!containsPoint(point)) {
            throw new IllegalStateException("해당 좌표에 기물이 존재하지 않습니다.");
        }
        return hurdles.get(point);
    }
}
