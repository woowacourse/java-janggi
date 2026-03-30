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
        if (isSameTeam(target)) {
            throw new IllegalArgumentException(PieceExceptionMessage.DESTINATION_HAS_ALLY.getMessage());
        }
        if (target instanceof Po) { // 기필코 바꾸겠다는 의지를 표명하는 instanceOf
            throw new IllegalArgumentException(PieceExceptionMessage.PO_CANT_CAPTURE_PO.getMessage());
        }
    }
}
