package move;

import exception.InvalidMovePosition;
import java.util.ArrayList;
import java.util.List;
import piece.Position;
import piece.Route;
import piece.Team;

public class ChaMoveBehavior implements MoveBehavior {

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position smallerPosition = startPosition.getSmallerPosition(endPosition);
        Position biggerPosition = startPosition.getBiggerPosition(endPosition);

        List<Position> positions = new ArrayList<>();
        return calculateSameLineRoute(startPosition, endPosition, smallerPosition, biggerPosition, positions);
    }

    private Route calculateSameLineRoute(Position startPosition, Position endPosition, Position minPosition,
                                         Position maxPosition, List<Position> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.UP);
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, Direction.RIGHT);
        }
        throw new InvalidMovePosition();
    }

    private Route calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
                                      Direction direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return new Route(positions);
    }
}
