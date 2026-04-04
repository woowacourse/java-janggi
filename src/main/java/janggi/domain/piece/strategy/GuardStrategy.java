package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Camp;
import janggi.domain.piece.PieceStrategy;
import janggi.exception.ExceptionMessage;

public class GuardStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;


    @Override
    public void validate(Position source, Position destination, Camp camp, BoardChecker board, PieceStrategy pieceStrategy) {
        DirectionInformation direction = new DirectionInformation(source, destination);
        validateDistance(direction);
    }

    private void validateDistance(DirectionInformation direction) {
        if (direction.isInvalidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
        }
    }
}
