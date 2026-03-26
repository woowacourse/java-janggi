package pieces;

import java.util.ArrayList;
import java.util.List;
import movepolicy.destination.BasicDestinationRule;
import movepolicy.destination.DestinationRule;
import movepolicy.path.EmptyPathRule;
import movepolicy.path.PathRule;
import position.Position;

public class Cha extends PieceImpl {

    public Cha(Side side) {
        super(side);
    }

    @Override
    void validateDestination(Position departure, Position destination) {
        if (!departure.isSameRow(destination) && !departure.isSameColumn(destination)) {
            throw new IllegalArgumentException("차의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
    }

    @Override
    List<Position> getPathPositions(Position departure, Position destination) {
        List<Position> pathPositions = new ArrayList<>();
        if (departure.isSameRow(destination)) {
            if (destination.isLeftColumn(departure)) {
                while (!departure.moveLeft().equals(destination)) {
                    departure = departure.moveLeft();
                    pathPositions.add(departure);
                }
            } else {
                while (!departure.moveRight().equals(destination)) {
                    departure = departure.moveRight();
                    pathPositions.add(departure);
                }
            }
        }
        if (departure.isSameColumn(destination)) {
            if (destination.isLowerRowThan(departure)) {
                while (!departure.moveDown().equals(destination)) {
                    departure = departure.moveDown();
                    pathPositions.add(departure);
                }
            } else {
                while (!departure.moveUp().equals(destination)) {
                    departure = departure.moveUp();
                    pathPositions.add(departure);
                }
            }
        }
        return pathPositions;
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
