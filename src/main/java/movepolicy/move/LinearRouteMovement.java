package movepolicy.move;

import java.util.ArrayList;
import java.util.List;
import pieces.Side;
import position.Delta;
import position.Position;

public class LinearRouteMovement implements Movement {

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        return departure.isSameRow(destination) || departure.isSameColumn(destination);
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        validateCanReach(departure, destination, side);

        final Step step = decideDirection(departure, destination, side);
        final List<Position> positions = new ArrayList<>();
        Position current = departure;
        while (step.canMove(current, side) && !step.move(current, side).equals(destination)) {
            current = step.move(current, side);
            positions.add(current);
        }
        return List.copyOf(positions);
    }

    private void validateCanReach(final Position departure, final Position destination, final Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("직선 이동이 아닙니다.");
        }
    }

    private Step decideDirection(final Position departure, final Position destination, final Side side) {
        if (departure.isSameRow(destination)) {
            return decideRightOrLeft(departure, destination, side);
        }
        if (departure.isSameColumn(destination)) {
            return decideForwardOrBack(departure, destination, side);
        }
        throw new IllegalArgumentException("직선 이동이 아닙니다.");
    }

    private Step decideForwardOrBack(final Position departure, final Position destination, final Side side) {
        final Delta delta = departure.calculateDeltaTo(destination);
        if (isForward(delta, side)) {
            return Step.FORWARD;
        }
        return Step.BACK;
    }

    private Step decideRightOrLeft(final Position departure, final Position destination, final Side side) {
        final Delta delta = departure.calculateDeltaTo(destination);
        if (isLeft(delta, side)) {
            return Step.LEFT;
        }
        return Step.RIGHT;
    }

    private boolean isForward(final Delta delta, final Side side) {
        if (side.isCho()) {
            return delta.isRowPositive();
        }
        return delta.isRowNegative();
    }

    private boolean isLeft(final Delta delta, final Side side) {
        if (side.isCho()) {
            return delta.isColumnNegative();
        }
        return delta.isColumnPositive();
    }
}
