package janggi.domain.board.coordination;

import janggi.domain.point.Point;

public class PalaceCoordination {
    private static final int CHO_MIN_X = 0;
    private static final int CHO_MAX_X = 2;
    private static final int HAN_MIN_X = 7;
    private static final int HAN_MAX_X = 9;
    private static final int MIN_Y = 3;
    private static final int MAX_Y = 5;

    private PalaceCoordination() {
        /* This utility class should not be instantiated */
    }

    public static boolean isInRange(Point point) {
        boolean choXInRange = point.x() >= CHO_MIN_X && point.x() <= CHO_MAX_X;
        boolean hanXInRange = point.x() >= HAN_MIN_X && point.x() <= HAN_MAX_X;
        return point.y() >= MIN_Y && point.y() <= MAX_Y && (choXInRange || hanXInRange);
    }
}
