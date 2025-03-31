package janggi.domain.piece.palace;

import janggi.domain.piece.Point;
import java.util.List;

public class ChuPalace extends Palace {
    @Override
    protected List<Point> palacePoints() {
        return List.of(new Point(8, 4), new Point(8, 5), new Point(8, 6),
                new Point(9, 4), new Point(9, 5), new Point(9, 6),
                new Point(10, 4), new Point(10, 5), new Point(10, 6));
    }

    @Override
    protected List<Point> diagonalPoints() {
        return List.of(new Point(8, 4), new Point(8, 6),
                new Point(9, 5),
                new Point(10, 4), new Point(10, 6));
    }
}
