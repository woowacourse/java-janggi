package janggi.domain.game.rule;

import janggi.domain.piece.Piece;
import janggi.domain.piece.fixed.General;
import janggi.domain.point.Point;
import janggi.domain.side.Side;
import java.util.Collection;
import java.util.Map;

public class GeneralDeadRule implements Rule {
    private static final Piece CHO_GENERAL = new General(Side.CHO);
    private static final Piece HAN_GENERAL = new General(Side.HAN);

    @Override
    public boolean isEnd(Map<Point, Piece> piecesWithPoint) {
        Collection<Piece> pieces = getPieces(piecesWithPoint);
        return !pieces.contains(CHO_GENERAL) || !pieces.contains(HAN_GENERAL);
    }

    @Override
    public Side getWinSide(Map<Point, Piece> piecesWithPoint) {
        Collection<Piece> pieces = getPieces(piecesWithPoint);
        if (pieces.contains(CHO_GENERAL)) {
            return Side.CHO;
        }
        return Side.HAN;
    }
}
