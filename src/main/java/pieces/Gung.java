package pieces;

import static movepolicy.move.OneStep.BACK;
import static movepolicy.move.OneStep.FORWARD;
import static movepolicy.move.OneStep.LEFT;
import static movepolicy.move.OneStep.RIGHT;

import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.move.FixedRouteMovement;
import movepolicy.move.Movement;
import movepolicy.move.Route;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Gung extends FullPiece {

    private static final Movement MOVEMENT = new FixedRouteMovement(List.of(
        new Route(List.of(FORWARD)),
        new Route(List.of(BACK)),
        new Route(List.of(RIGHT)),
        new Route(List.of(LEFT))
    ));

    public Gung(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("궁의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return MOVEMENT.getPathPositions(departure, destination, side);
    }

    @Override
    protected DestinationRule getDestinationRule() {
        return new BasicDestinationRule();
    }

    @Override
    protected PathRule getPathRule() {
        return new EmptyPathRule();
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
