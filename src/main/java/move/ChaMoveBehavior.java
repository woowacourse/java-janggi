package move;

import java.util.ArrayList;
import java.util.List;
import piece.Position;
import piece.Route;
import piece.Team;

public class ChaMoveBehavior implements MoveBehavior {

    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position minPosition = Position.getMinPosition(startPosition, endPosition);
        Position maxPosition = Position.getMaxPosition(startPosition, endPosition);

        List<Position> positions = new ArrayList<>();
        return calculateSameLineRoute(startPosition, endPosition, minPosition, maxPosition, positions);
    }

    private static Route calculateSameLineRoute(Position startPosition, Position endPosition, Position minPosition,
                                                Position maxPosition, List<Position> positions) {
        if (startPosition.isSameColumn(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, new Position(1, 0));
        }
        if (startPosition.isSameRow(endPosition)) {
            return calculateLegalRoute(minPosition, maxPosition, positions, new Position(0, 1));
        }
        throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
    }

    private static Route calculateLegalRoute(Position minPosition, Position maxPosition, List<Position> positions,
                                             Position direction) {
        while (!minPosition.equals(maxPosition)) {
            minPosition = minPosition.add(direction);
            positions.add(minPosition);
        }
        return new Route(positions);
    }
}
