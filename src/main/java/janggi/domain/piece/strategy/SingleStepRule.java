package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceRule;
import janggi.exception.ExceptionMessage;

public class SingleStepRule extends BaseMoveRule {

    private static final int SINGLE_STEP_DISTANCE = 1;

    private final boolean forwardOnly;

    public SingleStepRule(boolean forwardOnly) {
        this.forwardOnly = forwardOnly;
    }

    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceRule pieceRule) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateDistance(direction);

        if (forwardOnly) {
            validateForwardDirection(direction.calculateRowDirection(), camp);
        }
    }

    private void validateDistance(DirectionInformation direction) {
        if (direction.calculateDistance() != SINGLE_STEP_DISTANCE) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(SINGLE_STEP_DISTANCE));
        }
    }

    private void validateForwardDirection(int rowDirection, Camp camp) {
        boolean isRowMove = rowDirection != 0;
        boolean isForward = camp.matchesForwardDirection(rowDirection);

        if (isRowMove && !isForward) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_BACKWARD_MOVEMENT.getMessage());
        }
    }
}
