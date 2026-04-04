package movepolicy.move;

import java.util.List;
import movepolicy.rule.Gungsung;
import pieces.Side;
import position.Delta;
import position.Position;

public class GungsungForwardDiagonalMovement implements Movement {

    private final Gungsung gungsung = new Gungsung();

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        Delta delta = departure.calculateDelta(destination);
        return delta.isForwardOneStepDiagonal(side)
            && gungsung.isOneStepDiagonalInside(departure, destination);
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        if (!canReach(departure, destination, side)) {
            throw new IllegalArgumentException("유효하지 않은 이동입니다.");
        }
        return List.of();
    }
}
