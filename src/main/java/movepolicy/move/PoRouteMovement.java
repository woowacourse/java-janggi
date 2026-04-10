package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class PoRouteMovement implements Movement {

    private final Movement origin = new LinearRouteMovement();

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        if (!departure.isGapBiggerThanOneStep(destination)) {
            return false;
        }
        return origin.canReach(departure, destination, side);
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("유효하지 않은 이동입니다.");
        }
        return origin.findPathPositions(departure, destination, side);
    }
}
