package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;


    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateDistance(direction);
        validateForwardDirection(direction.calculateRowDirection(), camp);
    }

    private void validateDistance(DirectionInformation direction) {
        if (direction.isInvalidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
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
