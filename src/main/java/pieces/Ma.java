package pieces;

import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Ma extends FullPiece {

    public Ma(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        List<Position> movableDestinations = List.of(
                departure.moveUp().moveRightUp(),
                departure.moveUp().moveLeftUp(),
                departure.moveDown().moveRightDown(),
                departure.moveDown().moveLeftDown(),
                departure.moveLeft().moveLeftUp(),
                departure.moveLeft().moveLeftDown(),
                departure.moveRight().moveRightUp(),
                departure.moveRight().moveRightDown());
        if (!movableDestinations.contains(destination)) {
            throw new IllegalArgumentException("마의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
        if (departure.moveUp().moveUp().isSameRow(destination)) {
            return List.of(departure.moveUp());
        }
        if (departure.moveDown().moveDown().isSameRow(destination)) {
            return List.of(departure.moveDown());
        }
        if (departure.moveLeft().moveLeft().isSameColumn(destination)) {
            return List.of(departure.moveLeft());
        }
        if (departure.moveRight().moveRight().isSameColumn(destination)) {
            return List.of(departure.moveRight());
        }
        throw new IllegalArgumentException("출발지와 도착지의 좌표가 유효하지 않습니다.");
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
    public PieceType getType() {
        return PieceType.MA;
    }
}
