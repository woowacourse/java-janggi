package pieces;

import java.util.List;
import movepolicy.destination.DestinationRule;
import movepolicy.destination.PoDestinationRule;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.path.PathRule;
import movepolicy.path.PoPathRule;
import position.Position;

public class Po extends FullPiece {

    private static final Movement MOVEMENT = new LinearRouteMovement();

    public Po(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("포의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
        if (!departure.isGapBiggerThanOne(destination)) {
            throw new IllegalArgumentException("포는 한 칸만 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return MOVEMENT.getPathPositions(departure, destination, side);
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new PoDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new PoPathRule();
    }

    @Override
    public boolean isPo() {
        return true;
    }
}
