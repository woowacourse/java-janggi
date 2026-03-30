package domain.piece.policy;

import domain.PieceExceptionMessage;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;
import java.util.Map;

public class PoMovementPolicy implements MovementPolicy {

    public static final int REQUIRED_JUMP_COUNT = 1;

    @Override
    public void check(Map<Position, Piece> positionInfoOfBoard, List<Position> movablePath) {
        int jumpedPieces = 0;
        for (Position position : movablePath) {
            Piece pieceToCheck = positionInfoOfBoard.get(position);
            if (pieceToCheck == null) {
                continue;
            }
            if (!pieceToCheck.canBeJumpedOver()) {
                throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
            }
            jumpedPieces += 1;
        }
        checkIsInvalidJumpedPieces(jumpedPieces);
    }

    private void checkIsInvalidJumpedPieces(int jumpedPieces) {
        if (jumpedPieces > REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_THAN_TWO_PIECES.getMessage());
        }
        if (jumpedPieces < REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.PO_SHOULD_JUMP_ONE_PIECE.getMessage());
        }
    }
}
