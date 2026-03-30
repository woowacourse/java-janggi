package domain.piece;

import domain.PieceExceptionMessage;
import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Po extends Piece {

    public Po(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.PO, team);
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
