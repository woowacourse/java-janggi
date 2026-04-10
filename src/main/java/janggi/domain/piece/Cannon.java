package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Cannon extends Piece {
    public Cannon(Camp camp, MoveStrategy moveStrategy) {
        super(PieceInfo.from(camp, 7), moveStrategy);
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
        if (isSameCamp(piece)) {
            return false;
        }
        return piece.canBeCapturedByCannon();
    }

    @Override
    public boolean canBeJumpedOver() {
        return false;
    }

    @Override
    public boolean canBeCapturedByCannon() {
        return false;
    }

    @Override
    protected String pieceDisplayName(Camp camp) {
        return PieceDisplayName.CANNON.findDisplayName(camp);
    }
}
