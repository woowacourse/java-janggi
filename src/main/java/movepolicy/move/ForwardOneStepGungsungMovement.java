package movepolicy.move;

import java.util.List;
import movepolicy.rule.Gungsung;
import pieces.Side;
import position.Delta;
import position.Position;

public class ForwardOneStepGungsungMovement implements Movement {

    private final Gungsung gungsung = new Gungsung();

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        Delta delta = departure.calucalteDelta(destination);
        return delta.isForwardOneStepDiagonal(side)
            && gungsung.isDiagonalOneStepInside(departure, destination);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        return List.of();
    }
}
