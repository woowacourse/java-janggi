package janggi.piece.rule.movement;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.coordinate.Route;
import janggi.piece.rule.block.RequiredBlockCountRule;

public class StraightMovementRule extends MovementRule {

    private StraightMovementRule(final RequiredBlockCountRule requiredBlockCountRule) {
        super(requiredBlockCountRule);
    }

    public static MovementRule withNonBlock() {
        return new StraightMovementRule(RequiredBlockCountRule.withNonBlock());
    }

    public static MovementRule withBlock(final int requireBlockCount) {
        return new StraightMovementRule(RequiredBlockCountRule.withBlock(requireBlockCount));
    }

    @Override
    protected void validateMoveShape(final Board board, final Position departure, final Position destination) {
        final Distance distance = Distance.of(departure, destination);

        if (distance.isStraight()) {
            return;
        }

        if (isValidPalaceDiagonalMove(board, departure, destination, distance)) {
            return;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);
    }

    private boolean isValidPalaceDiagonalMove(final Board board, final Position departure, final Position destination, final Distance distance) {
        if (!board.isPalace(departure)) {
            return false;
        }

        if (!board.isPalace(destination)) {
            return false;
        }

        if (!distance.isDiagonal()) {
            return false;
        }

        final Route route = Route.of(departure, destination);

        return route.calculateWithDepartureAndDestination().stream()
                .anyMatch(board::isCenterOfPalace);
    }
}
