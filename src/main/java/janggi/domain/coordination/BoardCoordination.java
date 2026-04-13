package janggi.domain.coordination;

import janggi.domain.point.Point;

public class BoardCoordination {
    private static final int MIN_X = 0;
    private static final int MAX_X = 9;
    private static final int MIN_Y = 0;
    private static final int MAX_Y = 8;

    private BoardCoordination() {
        /* This utility class should not be instantiated */
    }

    public static boolean isInRange(Point point) {
        return point.x() >= MIN_X && point.x() <= MAX_X && point.y() >= MIN_Y && point.y() <= MAX_Y;
    }
}
