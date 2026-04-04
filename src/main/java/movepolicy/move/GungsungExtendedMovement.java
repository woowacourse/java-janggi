package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class GungsungExtendedMovement implements Movement {

    private final Movement baseMovement;
    private final Movement gungsungMovement;

    public GungsungExtendedMovement(Movement baseMovement, Movement gungsungMovement) {
        this.baseMovement = baseMovement;
        this.gungsungMovement = gungsungMovement;
    }

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        return baseMovement.canReach(departure, destination, side)
            || gungsungMovement.canReach(departure, destination, side);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        if (baseMovement.canReach(departure, destination, side)) {
            return baseMovement.findPathPositions(departure, destination, side);
        }
        return gungsungMovement.findPathPositions(departure, destination, side);
    }
}
