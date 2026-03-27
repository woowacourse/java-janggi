package pieces;

import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Sang extends PieceImpl {

    public Sang(Side side) {
        super(side);
    }

    @Override
    void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations = List.of(
            departure.moveUp().moveRightUp().moveRightUp(),
            departure.moveUp().moveLeftUp().moveLeftUp(),
            departure.moveDown().moveRightDown().moveRightDown(),
            departure.moveDown().moveLeftDown().moveLeftDown(),
            departure.moveLeft().moveLeftUp().moveLeftUp(),
            departure.moveLeft().moveLeftDown().moveLeftDown(),
            departure.moveRight().moveRightUp().moveRightUp(),
            departure.moveRight().moveRightDown().moveRightDown());
        if (!movableDestinations.contains(destination)) {
            throw new IllegalArgumentException("상의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    List<Position> getPathPositions(Position departure, Position destination) {
        if (departure.moveUp().moveRightUp().moveRightUp().equals(destination)) {
            return List.of(departure.moveUp(), departure.moveUp().moveRightUp());
        }
        if (departure.moveUp().moveLeftUp().moveLeftUp().equals(destination)) {
            return List.of(departure.moveUp(), departure.moveUp().moveLeftUp());
        }
        if (departure.moveDown().moveRightDown().moveRightDown().equals(destination)) {
            return List.of(departure.moveDown(), departure.moveDown().moveRightDown());
        }
        if (departure.moveDown().moveLeftDown().moveLeftDown().equals(destination)) {
            return List.of(departure.moveDown(), departure.moveDown().moveLeftDown());
        }
        if (departure.moveLeft().moveLeftUp().moveLeftUp().equals(destination)) {
            return List.of(departure.moveLeft(), departure.moveLeft().moveLeftUp());
        }
        if (departure.moveLeft().moveLeftDown().moveLeftDown().equals(destination)) {
            return List.of(departure.moveLeft(), departure.moveLeft().moveLeftDown());
        }
        if (departure.moveRight().moveRightUp().moveRightUp().equals(destination)) {
            return List.of(departure.moveRight(), departure.moveRight().moveRightUp());
        }
        if (departure.moveRight().moveRightDown().moveRightDown().equals(destination)) {
            return List.of(departure.moveRight(), departure.moveRight().moveRightDown());
        }
        throw new IllegalArgumentException("출발지와 도착지의 좌표가 유효하지 않습니다.");
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
