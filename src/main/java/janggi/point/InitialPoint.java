package janggi.point;

import java.util.Collections;
import java.util.List;

public enum InitialPoint {
    GUNG(List.of(new Point(1, 4)),
            List.of(new Point(8, 4))),
    SA(List.of(new Point(0, 3), new Point(0, 5)),
            List.of(new Point(9, 3), new Point(9, 5))),
    MA(List.of(new Point(0, 1), new Point(0, 7)),
            List.of(new Point(9, 1), new Point(9, 7))),
    SANG(List.of(new Point(0, 2), new Point(0, 6)),
            List.of(new Point(9, 2), new Point(9, 6))),
    CHA(List.of(new Point(0, 0), new Point(0, 8)),
            List.of(new Point(9, 0), new Point(9, 8))),
    PO(List.of(new Point(2, 1), new Point(2, 7)),
            List.of(new Point(7, 1), new Point(7, 7))),
    BYEONG(List.of(new Point(3, 0), new Point(3, 2), new Point(3, 4),
            new Point(3, 6), new Point(3, 8)),
            List.of(new Point(6, 0), new Point(6, 2), new Point(6, 4),
                    new Point(6, 6), new Point(6, 8)));

    private final List<Point> redPoints;
    private final List<Point> bluePoints;

    InitialPoint(List<Point> redPoints, List<Point> bluePoints) {
        this.redPoints = redPoints;
        this.bluePoints = bluePoints;
    }

    public List<Point> getRedPoints() {
        return Collections.unmodifiableList(redPoints);
    }

    public List<Point> getBluePoints() {
        return Collections.unmodifiableList(bluePoints);
    }
}
