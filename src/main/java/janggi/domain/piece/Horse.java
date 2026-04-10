package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Horse extends Piece {
    public Horse(Camp camp, MoveStrategy moveStrategy) {
        super(PieceInfo.from(camp, 5), moveStrategy);
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
    public boolean canBeCapturedByCannon() {
        return true;
    }

    @Override
    protected String pieceDisplayName(Camp camp) {
        return PieceDisplayName.HORSE.findDisplayName(camp);
    }
}
