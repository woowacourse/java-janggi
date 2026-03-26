package pieces;

import movepolicy.destination.DestinationRule;
import movepolicy.path.EmptyPathRule;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.path.PathRule;
import java.util.List;
import position.Position;

public class Gung extends PieceImpl {

    public Gung(Side side) {
        super(side);
    }

    @Override
    void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations = List.of(
            departure.moveUp(),
            departure.moveDown(),
            departure.moveLeft(),
            departure.moveRight());
        if (!movableDestinations.contains(destination)) {
            throw new IllegalArgumentException();
        }
    }

    @Override
    List<Position> getPathPositions(Position departure, Position destination) {
        return List.of();
    }

    @Override
    DestinationRule getDestinationRule() {
        return new BasicDestinationRule();
    }

    @Override
    PathRule getPathRule() {
        return new EmptyPathRule();
    }

    @Override
    public boolean isPo() {
        return false;
    }
}
