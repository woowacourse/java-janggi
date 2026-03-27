package pieces;

import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Gung extends FullPiece {

    public Gung(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations = List.of(
            departure.moveUp(),
            departure.moveDown(),
            departure.moveLeft(),
            departure.moveRight());
        if (!movableDestinations.contains(destination)) {
            throw new IllegalArgumentException("궁의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        return List.of();
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
