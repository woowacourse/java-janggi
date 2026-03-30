package domain.piece;

import domain.piece.policy.MovementPolicy;
import domain.piece.strategy.MoveStrategy;

public abstract class NonJumpable extends Piece {
    protected NonJumpable(MoveStrategy moveStrategy, MovementPolicy movementPolicy, PieceType pieceType, Team team) {
        super(moveStrategy, movementPolicy, pieceType, team);
    }

    @Override
    public boolean jumpable() {
        return true;
    }

    @Override
    public boolean isEatable(Piece destinationPiece) {
        return true;
    }
}
