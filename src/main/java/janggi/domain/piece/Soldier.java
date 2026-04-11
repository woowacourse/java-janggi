package janggi.domain.piece;

import janggi.domain.Camp;
import janggi.domain.Position;
import janggi.domain.piece.strategy.MoveStrategy;

import java.util.Map;

public class Soldier extends Piece {
    public Soldier(Camp camp, MoveStrategy moveStrategy) {
        super(PieceInfo.from(camp, 2), moveStrategy);
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
    protected PieceDisplayName pieceDisplayName() {
        return PieceDisplayName.SOLDIER;
    }
}
