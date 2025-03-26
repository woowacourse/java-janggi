package janggi.piece.strategy.move;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;

public class CurvedMoveStrategy implements MoveStrategy {

    private final int straightMovement;
    private final int diagonalMovement;

    public CurvedMoveStrategy(final int straightMovement, final int diagonalMovement) {
        validateCurvedMovement(straightMovement, diagonalMovement);

        this.straightMovement = straightMovement;
        this.diagonalMovement = diagonalMovement;
    }

    private void validateCurvedMovement(final int straightMovement, final int diagonalMovement) {
        if (straightMovement == 0 || diagonalMovement == 0) {
            throw new IllegalArgumentException("직선과 대각선으로 모두 움직여야합니다.");
        }
    }

    @Override
    public void validate(final Board board, final Position departure, final Position destination) {
        Distance distance = Distance.of(departure, destination);

        if (distance.getStraight() == straightMovement && distance.getDiagonal() == diagonalMovement) {
            return;
        }
        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
