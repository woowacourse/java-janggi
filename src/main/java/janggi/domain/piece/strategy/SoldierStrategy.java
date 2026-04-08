package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.PieceRule;
import janggi.domain.piece.camp.CampType;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy extends PalaceStrategy {

    private static final int DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, CampType campType, BoardChecker board, PieceRule pieceRule) {
        Movement movement = new Movement(source, destination);
        if (isPalaceRange(source, destination)) {
            validatePalaceDiagonalDistance(source, destination, movement);
            validateForwardMovement(movement.calculateRowDirection(), campType);
            return;
        }
        validateDistance(movement);
        validateForwardMovement(movement.calculateRowDirection(), campType);
    }

    private void validateDistance(Movement movement) {
        if (movement.isInvalidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
        }
    }

    private void validateForwardMovement(int rowDistance, CampType campType) {
        boolean isRowMove = rowDistance != 0;
        boolean isForward = campType.matchesForwardDirection(rowDistance);

        if (isRowMove && !isForward) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }
}
