package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Piece;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy extends PalaceStrategy {

    private static final int DISTANCE = 1;

    @Override
    protected void validatePalaceMove(Position source, Position destination, Movement movement, BoardChecker board) {
        if (isPalaceDiagonalPath(source, destination, movement)) {
            validatePalaceStepDistance(movement, DISTANCE);
            validateForwardMovement(source, movement, board);
            return;
        }
        validateNormalMove(source, destination, movement, board);
    }

    @Override
    protected void validateNormalMove(Position source, Position destination, Movement movement, BoardChecker board) {
        validateDistance(movement);
        validateForwardMovement(source, movement, board);
    }

    private void validateDistance(Movement movement) {
        if (!movement.isValidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
        }
    }

    private void validateForwardMovement(Position source, Movement movement, BoardChecker board) {
        if (movement.isHorizontal()) {
            return;
        }
        Piece piece = board.pieceAt(source);
        if (!piece.campType().matchesForwardDirection(movement.calculateRowDirection())) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }
}
