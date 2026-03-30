package domain.piece.policy;

import domain.BoardStatus;
import domain.PieceExceptionMessage;
import domain.position.Position;
import java.util.List;

public class PoMovementPolicy implements MovementPolicy {

    public static final int REQUIRED_JUMP_COUNT = 1;

    @Override
    public boolean isMovable(BoardStatus boardStatus, List<Position> movablePath) {
        int jumpedPieces = boardStatus.getCountOfJumpablePieces(movablePath);
        checkIsInvalidJumpedPieces(jumpedPieces);
//        checkPoLocatesAtDestination(boardStatus.getBoardStatus().get(destination));
        return true;
    }

    private void checkIsInvalidJumpedPieces(int jumpedPieces) {
        if (jumpedPieces > REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_THAN_TWO_PIECES.getMessage());
        }
        if (jumpedPieces < REQUIRED_JUMP_COUNT) {
            throw new IllegalArgumentException(PieceExceptionMessage.PO_SHOULD_JUMP_ONE_PIECE.getMessage());
        }
    }

//    private void checkPoLocatesAtDestination(Piece destinationPiece) {
//        if (destinationPiece != null && !destinationPiece.jumpable()) {
//            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
//        }
//    }
}
