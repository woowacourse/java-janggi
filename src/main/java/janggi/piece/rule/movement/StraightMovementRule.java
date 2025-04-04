package janggi.piece.rule.movement;

import janggi.board.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.piece.rule.block.RequiredBlockCountRule;
import janggi.piece.rule.palace.PalaceDiagonalRule;
import janggi.piece.rule.palace.StraightMovementPalaceDiagonalRule;

public class StraightMovementRule extends MovementRule {

    private final PalaceDiagonalRule palaceDiagonalRule;

    private StraightMovementRule(final RequiredBlockCountRule requiredBlockCountRule,
                                 final PalaceDiagonalRule palaceDiagonalRule) {
        super(requiredBlockCountRule);
        this.palaceDiagonalRule = palaceDiagonalRule;
    }

    public static MovementRule withNonBlock() {
        return new StraightMovementRule(RequiredBlockCountRule.withNonBlock(),
                new StraightMovementPalaceDiagonalRule());
    }

    public static MovementRule withBlock(final int requireBlockCount) {
        return new StraightMovementRule(RequiredBlockCountRule.withBlock(requireBlockCount),
                new StraightMovementPalaceDiagonalRule());
    }

    @Override
    protected void validateMovement(final Board board, final Position departure, final Position destination) {
        final Distance distance = Distance.of(departure, destination);

        if (distance.isStraight()
                || palaceDiagonalRule.isSatisfied(board, departure, destination, distance)) {
            return;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }
}
