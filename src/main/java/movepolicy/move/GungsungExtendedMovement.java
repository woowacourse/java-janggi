package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class GungsungExtendedMovement implements Movement {

    private final Movement baseMovement;
    private final Movement gungsungMovement;

    public GungsungExtendedMovement(final Movement baseMovement, final Movement gungsungMovement) {
        this.baseMovement = baseMovement;
        this.gungsungMovement = gungsungMovement;
    }

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        return baseMovement.canReach(departure, destination, side)
            || gungsungMovement.canReach(departure, destination, side);
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("유효하지 않은 이동입니다.");
        }
        if (baseMovement.canReach(departure, destination, side)) {
            return baseMovement.findPathPositions(departure, destination, side);
        }
        return gungsungMovement.findPathPositions(departure, destination, side);
    }
}
