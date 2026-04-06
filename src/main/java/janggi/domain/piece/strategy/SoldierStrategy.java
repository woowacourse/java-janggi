package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy extends PalaceStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        Movement movement = new Movement(source, destination);
        if (isPalaceRange(source, destination)) {
            validatePalaceDiagonalDistance(source, destination, movement);
            validateForwardMovement(movement.calculateRowDirection(), camp);
            return;
        }
        validateDistance(movement);
        validateForwardMovement(movement.calculateRowDirection(), camp);
    }

    private void validateDistance(Movement movement) {
        if (movement.isInvalidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
        }
    }

    private void validateForwardMovement(int rowDistance, Camp camp) {
        boolean isRowMove = rowDistance != 0;
        boolean isForward = camp.matchesForwardDirection(rowDistance);

        if (isRowMove && !isForward) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }
}
