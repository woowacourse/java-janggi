package domain.piece.policy;

import domain.PathContext;
import domain.PieceExceptionMessage;

public class PoMovementPolicy implements MovementPolicy {

    public static final int REQUIRED_JUMP_COUNT = 1;

    @Override
    public void check(PathContext pathContext) {
        if (pathContext.hasPo()) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
        }
        int existPieces = pathContext.getPieceCount();
        checkIsInvalidJumpedPieces(existPieces);
    }

    private void checkIsInvalidJumpedPieces(int existsPieces) {
        if (existsPieces > REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_THAN_TWO_PIECES.getMessage());
        }
        if (existsPieces < REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.PO_SHOULD_JUMP_ONE_PIECE.getMessage());
        }
    }
}
