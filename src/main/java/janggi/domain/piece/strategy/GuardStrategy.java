package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.exception.ExceptionMessage;

public class GuardStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);

        if (!board.isPalaceRange(source, destination)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
        if (movement.exceedsDistance(DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
        if (movement.isDiagonal() && !board.isAllowedDiagonalPath(source, destination)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
        }
    }
}
