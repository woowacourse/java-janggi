package janggi.domain.piece.palace;

import janggi.domain.piece.Point;
import java.util.List;

public class HanPalace extends Palace {
    @Override
    protected List<Point> palacePoints() {
        return List.of(new Point(1, 4), new Point(1, 5), new Point(1, 6),
                new Point(2, 4), new Point(2, 5), new Point(2, 6),
                new Point(3, 4), new Point(3, 5), new Point(3, 6));
    }

    @Override
    protected List<Point> diagonalPoints() {
        return List.of(new Point(1, 4), new Point(1, 6),
                new Point(2, 5),
                new Point(3, 4), new Point(3, 6));
    }
}
