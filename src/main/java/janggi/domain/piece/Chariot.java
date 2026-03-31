package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.LinearStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Chariot extends Piece {
    private static final LinearStrategy LINEAR_STRATEGY = new LinearStrategy();

    public Chariot(Camp camp) {
        super(camp, LINEAR_STRATEGY);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        return piecesInPath.isEmpty();
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
        return displayName("車", "車");
    }
}
