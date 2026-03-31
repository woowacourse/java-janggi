package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Cannon extends Piece {
    public Cannon(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
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
