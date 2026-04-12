package domain.piece.policy;

import domain.Board;
import domain.PieceExceptionMessage;
import domain.piece.Piece;
import domain.position.Position;
import java.util.List;

public class PoMovementPolicy implements MovementPolicy {

    public static final int REQUIRED_JUMP_COUNT = 1;

    @Override
    public void validate(Board board, List<Position> path, Position start, Position destination) {
        int pieceCount = 0;
        for (int index = 0; index < path.size() - 1; index++) {
            Position current = path.get(index);
            Piece piece = null;
            if ((piece = board.getPieceWithNull(current)) != null) {
                pieceCount += 1;
                validateIsJumpable(piece);
            }
        }
        checkIsInvalidJumpedPieces(pieceCount);
    }

    private static void validateIsJumpable(Piece piece) {
        if (!piece.canBeJumpedOver()) {
            throw new IllegalArgumentException(PieceExceptionMessage.CANT_JUMP_OVER_PO.getMessage());
        }
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
