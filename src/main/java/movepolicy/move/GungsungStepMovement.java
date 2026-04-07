package movepolicy.move;

import java.util.List;
import movepolicy.rule.Gungsung;
import pieces.Side;
import position.Delta;
import position.Position;

public class GungsungStepMovement implements Movement {

    private final Gungsung gungsung = new Gungsung();

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        final Delta delta = departure.calculateDeltaTo(destination);
        if (delta.isOneStepDiagonal()) {
            return gungsung.isOneStepDiagonalInside(departure, destination);
        }
        return gungsung.isInsideSameGungsung(departure, destination);
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("유효하지 않은 이동입니다.");
        }
        return List.of();
    }
}
