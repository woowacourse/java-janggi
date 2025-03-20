package strategy;

import java.util.ArrayList;
import java.util.List;
import piece.Position;
import piece.Route;
import piece.Team;

public class ChaMoveStrategy implements MoveStrategy {

    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition, Team team) {
        Position minPosition = Position.getMinPosition(startPosition, endPosition);
        Position maxPosition = Position.getMaxPosition(startPosition, endPosition);

        List<Position> positions = new ArrayList<>();
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
