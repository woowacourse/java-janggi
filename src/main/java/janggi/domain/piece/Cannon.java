package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Cannon extends Piece{
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
        if (pieceInPath.isCannon()) {
            return false;
        }
        return true;
    }

    @Override
    public boolean canCatch(Piece piece) {
        if (piece.isCannon()) {
            return false;
        }
        return !isSameCamp(piece);
    }

    @Override
    public boolean isCannon() {
        return true;
    }
}
