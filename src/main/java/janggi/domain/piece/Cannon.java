package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.piece.strategy.LinearStrategy;
import janggi.domain.position.Position;

import java.util.Map;

public class Cannon extends Piece {
    private static final LinearStrategy LINEAR_STRATEGY = new LinearStrategy();

    public Cannon(Camp camp) {
        super(camp, LINEAR_STRATEGY);
    }

    @Override
    public boolean canPassRoute(Map<Position, Piece> piecesInPath) {
        if (piecesInPath.size() != 1) {
            return false;
        }
        Piece pieceInPath = piecesInPath.values()
                .stream()
                .findFirst()
                .orElseThrow();
        return pieceInPath.canBeJumpedOver();
    }

    @Override
    public boolean canCatch(Piece piece) {
        if (!piece.canBeCaughtByCannon()) {
            return false;
        }
        return !isSameCamp(piece);
    }

    @Override
    public boolean canBeJumpedOver() {
        return false;
    }

    @Override
    public boolean canBeCaughtByCannon() {
        return false;
    }

    @Override
    public String displayHanja() {
        return displayName("包", "砲");
    }
}
