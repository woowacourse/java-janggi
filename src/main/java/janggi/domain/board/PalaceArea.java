package janggi.domain.board;

import java.util.Set;

public class PalaceArea {

    private final Set<Point> palaceArea;

    public PalaceArea() {
        this.palaceArea = Set.of(
                new Point(3, 0), new Point(4, 0), new Point(5, 0),
                new Point(3, 1), new Point(4, 1), new Point(5, 1),
                new Point(3, 2), new Point(4, 2), new Point(5, 2),
                new Point(3, 7), new Point(4, 7), new Point(5, 7),
                new Point(3, 8), new Point(4, 8), new Point(5, 8),
                new Point(3, 9), new Point(4, 9), new Point(5, 9)
        );
    }

    public boolean contains(Point point) {
        return palaceArea.contains(point);
    }
}
