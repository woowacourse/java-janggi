package janggi.piece.rule.movement;

import janggi.Board;
import janggi.coordinate.Distance;
import janggi.coordinate.Position;
import janggi.coordinate.Route;
import janggi.piece.rule.block.RequiredBlockCountRule;

public class SingleMovementRule extends MovementRule {

    public static final int SINGLE_STEP = 1;

    private SingleMovementRule(final RequiredBlockCountRule requiredBlockCountRule) {
        super(requiredBlockCountRule);
    }

    public static MovementRule withNonBlock() {
        return new SingleMovementRule(RequiredBlockCountRule.withNonBlock());
    }

    @Override
    protected void validateMoveShape(final Board board, final Position departure, final Position destination) {
        final Distance distance = Distance.of(departure, destination);

        if (isValidSingleStraightMove(distance)) {
            return;
        }

        if (isValidPalaceDiagonalMove(board, departure, destination, distance)) {
            return;
        }

        throw new IllegalArgumentException(EXCEPTION_MESSAGE);

    }

    private boolean isValidSingleStraightMove(final Distance distance) {
        return distance.getTotal() == SINGLE_STEP;
    }

    private boolean isValidPalaceDiagonalMove(final Board board, final Position departure, final Position destination, final Distance distance) {
        if (!board.isPalace(departure)) {
            return false;
        }

        if (!distance.isDiagonal()) {
            return false;
        }

        if (!(distance.getDiagonal() == SINGLE_STEP)) {
            return false;
        }

        final Route route = Route.of(departure, destination);

        return route.calculateWithDepartureAndDestination().stream()
                .anyMatch(board::isCenterOfPalace);
    }
}
