package janggi.board;

import janggi.board.point.Point;
import java.util.Set;

public final class Palace {

    private static final Set<Point> PALACE_POINTS = Set.of(
            new Point(3, 7), new Point(4, 7), new Point(5, 7),
            new Point(3, 8), new Point(4, 8), new Point(5, 8),
            new Point(3, 9), new Point(4, 9), new Point(5, 9),

            new Point(3, 0), new Point(4, 0), new Point(5, 0),
            new Point(3, 1), new Point(4, 1), new Point(5, 1),
            new Point(3, 2), new Point(4, 2), new Point(5, 2)
    );

    private static final Set<Point> PALACE_DIAGONAL_POINTS = Set.of(
            new Point(3, 7), new Point(5, 7),
            new Point(4, 8),
            new Point(3, 9), new Point(5, 9),

            new Point(3, 0), new Point(5, 0),
            new Point(4, 1),
            new Point(3, 2), new Point(5, 2)
    );

    private Palace() {
    }

    public static boolean isInsidePalace(Point point) {
        return PALACE_POINTS.contains(point);
    }

    public static boolean isDiagonalPalaceMoveAllowed(Point fromPoint, Point toPoint) {
        return PALACE_DIAGONAL_POINTS.contains(fromPoint)
                && PALACE_DIAGONAL_POINTS.contains(toPoint)
                && fromPoint.isDiagonal(toPoint);
    }
}
