package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class PoRouteMovement implements Movement {

    private final Movement movement;

    public PoRouteMovement(Movement movement) {
        this.movement = movement;
    }

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        if (!departure.isGapBiggerThanOne(destination)) {
            throw new IllegalArgumentException("포는 2칸 이상 이동할 수 있습니다.");
        }
        return movement.canReach(departure, destination, side);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        return movement.findPathPositions(departure, destination, side);
    }
}
