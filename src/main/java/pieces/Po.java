package pieces;

import java.util.ArrayList;
import java.util.List;
import movepolicy.destination.DestinationRule;
import movepolicy.destination.PoDestinationRule;
import movepolicy.path.PathRule;
import movepolicy.path.PoPathRule;
import position.Position;

public class Po extends FullPiece {

    public Po(Side side) {
        super(side);
    }

    @Override
    protected void validateDestination(Position departure, Position destination) {
        if (!departure.isSameRow(destination) && !departure.isSameColumn(destination)) {
            throw new IllegalArgumentException("포의 행마법으로는 해당 위치로 이동할 수 없습니다.");
        }
        if (!departure.isGapBiggerThanOne(destination)) {
            throw new IllegalArgumentException("포는 한 칸만 이동할 수 없습니다.");
        }
    }

    @Override
    protected List<Position> getPathPositions(Position departure, Position destination) {
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
