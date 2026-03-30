package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public class Po extends Piece {

    public Po(MoveStrategy moveStrategy, MovementPolicy movementPolicy, Team team) {
        super(moveStrategy, movementPolicy, PieceType.PO, team);
    }

    @Override
    public boolean jumpable() {
        return false;
    }

    @Override
    public boolean isEatable(Piece destinationPiece) {
        if (!destinationPiece.jumpable()) {
            throw new IllegalArgumentException("포는 포를 잡을 수 없어염");
        }
        return true;
    }
}
