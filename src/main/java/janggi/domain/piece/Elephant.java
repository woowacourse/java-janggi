package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.JanggiPosition;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Elephant extends Piece {
    public Elephant(Camp camp, MoveStrategy moveStrategy) {
        super(PieceInfo.from(camp, 3), moveStrategy);
    }

    @Override
    public boolean canPassRoute(Map<JanggiPosition, Piece> piecesInPath) {
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
    public boolean canBeCapturedByCannon() {
        return true;
    }

    @Override
    protected PieceDisplayName pieceDisplayName() {
        return PieceDisplayName.ELEPHANT;
    }
}
