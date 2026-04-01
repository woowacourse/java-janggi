package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.HorseStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Horse extends Piece {
    private static final HorseStrategy HORSE_STRATEGY = new HorseStrategy();

    public Horse(Camp camp) {
        super(camp, HORSE_STRATEGY);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return piecesInPath.isEmpty();
    }

    @Override
    public boolean canCatch(Piece piece) {
        return !this.isSameCamp(piece);
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
    public String choDisplayName() {
        return "馬";
    }

    @Override
    public String hanDisplayName() {
        return "馬";
    }
}
