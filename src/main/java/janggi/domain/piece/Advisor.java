package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Advisor extends Piece {
    public Advisor(Camp camp, MoveStrategy moveStrategy) {
        super(PieceInfo.from(camp, 3), moveStrategy);
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
    public boolean canBeJumpedOver() {
        return true;
    }

    @Override
    public boolean canBeCapturedByCannon() {
        return true;
    }

    @Override
    protected String pieceDisplayName(Camp camp) {
        return PieceDisplayName.ADVISOR.findDisplayName(camp);
    }
}
