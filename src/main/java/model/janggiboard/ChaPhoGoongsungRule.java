package model.janggiboard;

import model.Path;
import model.Point;

public class ChaPhoGoongsungRule {
    private final Point blueGoongsungCenterPoint;
    private final Point redGoongsungCenterPoint;

    public ChaPhoGoongsungRule() {
        this.blueGoongsungCenterPoint = Point.of(4, 1);
        this.redGoongsungCenterPoint = Point.of(4, 8);
    }

    public boolean isStartedInGoongsung(Point startPoint) {
        return isInsideGoongsung(blueGoongsungCenterPoint, startPoint) || isInsideGoongsung(redGoongsungCenterPoint,
                startPoint);
    }

    private boolean isInsideGoongsung(Point goongsungCenterPoint, Point point) {
        return Math.abs(goongsungCenterPoint.x() - point.x()) <= 1 &&
                Math.abs(goongsungCenterPoint.y() - point.y()) <= 1;
    }

    public boolean containsCenterPoint(Path path) {
        return path.getPath().stream()
                .anyMatch(point -> point.equals(blueGoongsungCenterPoint) || point.equals(redGoongsungCenterPoint));
    }
}
