package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.position.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Advisor extends Piece {
    public Advisor(Camp camp, MoveStrategy moveStrategy) {
        super(camp, moveStrategy);
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
    public boolean isCannon() {
        return false;
    }
}
