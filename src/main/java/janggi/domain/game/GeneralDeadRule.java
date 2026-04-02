package janggi.domain.game;

import janggi.domain.piece.unit.General;
import janggi.domain.piece.unit.Piece;
import janggi.domain.side.Side;
import java.util.Collection;

public class GeneralDeadRule implements Rule {
    private static final General CHO_GENERAL = new General(Side.CHO);
    private static final General HAN_GENERAL = new General(Side.HAN);

    @Override
    public boolean isEnd(Collection<Piece> pieces) {
        return !pieces.contains(CHO_GENERAL) || !pieces.contains(HAN_GENERAL);
    }

    @Override
    public Side getWinSide(Collection<Piece> pieces) {
        if (pieces.contains(CHO_GENERAL)) {
            return Side.HAN;
        }
        return Side.CHO;
    }
}
