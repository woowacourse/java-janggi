package movepolicy.move;

import java.util.List;
import movepolicy.rule.Gungsung;
import pieces.Side;
import position.Delta;
import position.Position;

public class GungsungForwardDiagonalMovement implements Movement {

    private final Gungsung gungsung = new Gungsung();

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        return isForwardOneStepDiagonal(departure, destination, side)
            && gungsung.isOneStepDiagonalInside(departure, destination);
    }

    private boolean isForwardOneStepDiagonal(final Position departure, final Position destination, final Side side) {
        final Delta delta = departure.calculateDeltaTo(destination);
        return delta.equals(side.leftForwardDelta()) || delta.equals(side.rightForwardDelta());
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("유효하지 않은 이동입니다.");
        }
        return List.of();
    }
}
