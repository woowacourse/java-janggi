package janggi.domain.piece.strategy;

import janggi.domain.Position;
import janggi.domain.board.BoardChecker;
import janggi.domain.piece.Piece;
import janggi.exception.ExceptionMessage;

public class SoldierStrategy implements MoveStrategy {

    private static final int DISTANCE = 1;

    @Override
    public void validate(Position source, Position destination, BoardChecker board) {
        Movement movement = new Movement(source, destination);

        if (board.isPalaceRange(source, destination) && board.isAllowedDiagonalPath(source, destination)) {
            validateDiagonalMove(source, movement, board);
            return;
        }
        validateStraightMove(source, movement, board);
    }

    private void validateDiagonalMove(Position source, Movement movement, BoardChecker board) {
        validateDiagonalDistance(movement);
        validateForwardMovement(source, movement, board);
    }

    private void validateDiagonalDistance(Movement movement) {
        if (movement.exceedsDistance(DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_PALACE_MOVE.getMessage(DISTANCE));
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

    private void validateStraightMove(Position source, Movement movement, BoardChecker board) {
        validateStraightDistance(movement);
        validateForwardMovement(source, movement, board);
    }

    private void validateStraightDistance(Movement movement) {
        if (!movement.isValidMoveDistance(0, DISTANCE)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_SINGLE_STEP_MOVE.getMessage(DISTANCE));
        }
    }
}
