package pieces;

import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.move.LinearRouteMovement;
import movepolicy.move.Movement;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Cha extends FullPiece {

    private static final Movement MOVEMENT = new LinearRouteMovement();

    public Cha(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!MOVEMENT.canReach(departure, destination, side)) {
            throw new IllegalArgumentException("차의 행마법으로는 해당 위치로 이동할 수 없습니다.");
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

    @Override
    public PieceType type() {
        return PieceType.CHA;
    }
}
