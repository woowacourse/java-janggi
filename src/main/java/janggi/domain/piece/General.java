package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.PalaceStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class General extends Piece {
    private static final PalaceStrategy PALACE_STRATEGY = new PalaceStrategy();

    public General(Camp camp) {
        super(camp, PALACE_STRATEGY);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return true;
    }

    @Override
    public boolean canCatch(Piece piece) {
        return !isSameCamp(piece);
    }

    @Override
    public boolean canBeJumpedOver() {
        return true;
    }

    @Override
    public boolean canBeCaughtByCannon() {
        return true;
    }

    @Override
    public String displayHanja() {
        return displayName("楚", "漢");
    }
}
