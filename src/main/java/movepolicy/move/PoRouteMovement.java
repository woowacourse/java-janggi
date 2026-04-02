package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class PoRouteMovement implements Movement {

    private final Movement origin = new LinearRouteMovement();

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        if (!departure.isGapBiggerThanOne(destination)) {
            return false;
        }
        return origin.canReach(departure, destination, side);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        return origin.findPathPositions(departure, destination, side);
    }
}
