package janggi.domain.board;

import java.util.List;

public enum Palace {
    HAN_PALACE (List.of(
            new Point(1, 4),
            new Point(1, 5),
            new Point(1, 6),
            new Point(2, 4),
            new Point(2, 5),
            new Point(2, 6),
            new Point(3, 4),
            new Point(3, 5),
            new Point(3, 6)
    )),
    CHU_PALACE(List.of(
            new Point(8, 4),
            new Point(8, 5),
            new Point(8, 6),
            new Point(9, 4),
            new Point(9, 5),
            new Point(9, 6),
            new Point(10, 4),
            new Point(10, 5),
            new Point(10, 6)
    ))
    ;

    private final List<Point> points;

    Palace(List<Point> points) {
        this.points = points;
    }

    public static boolean isInPalace(Point start, Point end) {
        if (CHU_PALACE.points.contains(start) && CHU_PALACE.points.contains(end)) {
            return true;
        }
        return HAN_PALACE.points.contains(start) && HAN_PALACE.points.contains(end);
    }

}

