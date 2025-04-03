package model.piece;

import java.util.List;
import model.Point;

public enum Palace {
    ALL_PALACE(List.of(new Point(3, 0), new Point(4, 1), new Point(3, 2), new Point(5, 0), new Point(5, 2),
            new Point(3, 9), new Point(4, 8), new Point(3, 7), new Point(5, 9), new Point(5, 7))),
    BLUE_PALACE(List.of(new Point(3, 0), new Point(4, 1), new Point(3, 2), new Point(5, 0), new Point(5, 2))),
    RED_PALACE(List.of(new Point(3, 9), new Point(4, 8), new Point(3, 7), new Point(5, 9), new Point(5, 7)));

    private final List<Point> points;

    Palace(List<Point> points) {
        this.points = points;
    }

    public static Palace wherePalace(Point point) {
        for (Palace palace : Palace.values()) {
            if (palace.getPoints().contains(point)) {
                return palace;
            }
        }
        throw new IllegalArgumentException("잘못된 탐색입니다.");
    }

    public List<Point> getPoints() {
        return points;
    }

    public boolean isInPalace(Point point) {
        return points.contains(point);
    }
}
