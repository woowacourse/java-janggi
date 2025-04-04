package janggi.piece.rule.movement;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.piece.rule.block.RequiredBlockCountRule;
import janggi.piece.rule.palace.PalaceDiagonalRule;
import janggi.piece.rule.palace.SingleMovementPalaceDiagonalRule;

public class SingleMovementRule extends MovementRule {

    public static final int SINGLE_STEP = 1;

    private final PalaceDiagonalRule palaceDiagonalRule;

    protected SingleMovementRule(final RequiredBlockCountRule requiredBlockCountRule,
                                 final PalaceDiagonalRule straightMovementPalaceDiagonalRule) {
        super(requiredBlockCountRule);
        this.palaceDiagonalRule = straightMovementPalaceDiagonalRule;
    }

    public static MovementRule withNonBlock() {
        return new SingleMovementRule(
                RequiredBlockCountRule.withNonBlock(),
                new SingleMovementPalaceDiagonalRule());
    }

    @Override
    protected void validateMovement(final Board board, final Position departure, final Position destination) {
        final Distance distance = Distance.of(departure, destination);

        if (isValidSingleStraightMove(distance)
                || palaceDiagonalRule.isSatisfied(board, departure, destination, distance)) {
            return;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    private boolean isValidSingleStraightMove(final Distance distance) {
        return distance.getTotal() == SINGLE_STEP;
    }
}
