package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Piece;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy extends PalaceStrategy {

    private static final int DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);
        if (isPalaceRange(source, destination)) {
            validatePalaceDiagonalDistance(source, destination, movement);
            validateForwardMovement(source, movement, board);
            return;
        }
        validateDistance(movement);
        validateForwardMovement(source, movement, board);
    }

    private void validateDistance(Movement movement) {
        if (movement.isInvalidMoveDistance(0, DISTANCE)) {
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
