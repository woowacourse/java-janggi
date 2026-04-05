package janggi.domain.board;

import janggi.domain.point.Point;

public class Castle {

    private static final CastleZone HAN_CASTLE = new CastleZone(Point.of(3, 9), Point.of(5, 7));
    private static final CastleZone CHO_CASTLE = new CastleZone(Point.of(3, 2), Point.of(5, 0));

    public boolean inSameCastle(Point from, Point to) {
        return (HAN_CASTLE.contains(from) && HAN_CASTLE.contains(to)) ||
                (CHO_CASTLE.contains(from) && CHO_CASTLE.contains(to));
    }
}
