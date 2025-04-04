package janggi.piece.rule.movement;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.piece.rule.block.RequiredBlockCountRule;

public class CurvedMovementRule extends MovementRule {

    private final int straightMovement;
    private final int diagonalMovement;

    private CurvedMovementRule(final RequiredBlockCountRule requiredBlockCountRule, final int straightMovement, final int diagonalMovement) {
        super(requiredBlockCountRule);
        validateCurvedMovement(straightMovement, diagonalMovement);

        this.straightMovement = straightMovement;
        this.diagonalMovement = diagonalMovement;
    }

    public static MovementRule withNonBlock(final int straightMovement, final int diagonalMovement) {
        return new CurvedMovementRule(RequiredBlockCountRule.withNonBlock(), straightMovement, diagonalMovement);
    }

    @Override
    protected void validateMovement(final Board board, final Position departure, final Position destination) {
        final Distance distance = Distance.of(departure, destination);

        if (matchesRequiredCurve(distance)) {
            return;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    private boolean matchesRequiredCurve(final Distance distance) {
        return distance.getStraight() == straightMovement && distance.getDiagonal() == diagonalMovement;
    }

    private void validateCurvedMovement(final int straightMovement, final int diagonalMovement) {
        if (straightMovement == 0 || diagonalMovement == 0) {
            throw new IllegalArgumentException("직선과 대각선으로 모두 움직여야합니다.");
        }
    }
}
