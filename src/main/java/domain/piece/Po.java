package domain.piece;

import domain.PieceExceptionMessage;
import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;
import java.util.List;

public class Po extends Piece {
    private static final double PO_SCORE = 7;

    public Po(MoveStrategy moveStrategy, List<MovementPolicy> movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, team, PieceType.PO, PO_SCORE);
    }

    @Override
    public boolean canBeJumpedOver() {
        return false;
    }

    @Override
    public void capture(Piece target) {
        super.capture(target);
        if (!target.canBeJumpedOver()) {
            throw new IllegalArgumentException(PieceExceptionMessage.PO_CANT_CAPTURE_PO.getMessage());
        }
    }
}