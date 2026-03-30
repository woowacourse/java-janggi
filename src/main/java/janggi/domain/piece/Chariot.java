package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Chariot extends Piece {
    public Chariot(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
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
}
