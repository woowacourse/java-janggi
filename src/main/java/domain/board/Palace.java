package domain.board;

public final class Palace {
    private static final Point PALACE_START_POINT_FOR_HAN = new Point(0, 3);
    private static final Point PALACE_END_POINT_FOR_HAN = new Point(2, 5);

    private static final Point PALACE_START_POINT_FOR_CHO = new Point(7, 3);
    private static final Point PALACE_END_POINT_FOR_CHO = new Point(9, 5);

    public static boolean isInRange(final Point start, final Point arrival) {
        return (start.isInSquareRange(PALACE_START_POINT_FOR_HAN, PALACE_END_POINT_FOR_HAN)
                && arrival.isInSquareRange(PALACE_START_POINT_FOR_HAN, PALACE_END_POINT_FOR_HAN))
                || (start.isInSquareRange(PALACE_START_POINT_FOR_CHO, PALACE_END_POINT_FOR_CHO)
                && arrival.isInSquareRange(PALACE_START_POINT_FOR_CHO, PALACE_END_POINT_FOR_CHO));
    }
}
