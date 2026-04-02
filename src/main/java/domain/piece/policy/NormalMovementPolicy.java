package domain.piece.policy;

import domain.PathContext;
import domain.PieceExceptionMessage;

public class NormalMovementPolicy implements MovementPolicy {
    @Override
    public void validate(PathContext pathContext) {
        if (pathContext.getPieceCount() > 0) {
            throw new IllegalArgumentException(PieceExceptionMessage.BLOCKED_BY_PIECE.getMessage());
        }
    }
}
