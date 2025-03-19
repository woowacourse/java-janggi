package strategy;

import java.util.ArrayList;
import java.util.List;
import piece.Position;
import piece.Route;

public class ChaMoveStrategy implements MoveStrategy {

    private static final String INVALID_MOVE_LOCATION = "이동불가능한 위치입니다.";

    @Override
    public Route getLegalRoute(Position startPosition, Position endPosition) {
        Position minPosition = Position.getMinPosition(startPosition, endPosition);
        Position maxPosition = Position.getMaxPosition(startPosition, endPosition);

        List<Position> positions = new ArrayList<>();
        if (startPosition.isSameColumn(endPosition)) {
            Position up = new Position(1, 0);
            while (!minPosition.equals(maxPosition)) {
                minPosition = minPosition.add(up);
                positions.add(minPosition);
            }
            return new Route(positions);
        }

        if (startPosition.isSameRow(endPosition)) {
            Position right = new Position(0, 1);
            while (!minPosition.equals(maxPosition)) {
                minPosition = minPosition.add(right);
                positions.add(minPosition);
            }
            return new Route(positions);
        }

        throw new IllegalArgumentException(INVALID_MOVE_LOCATION);
    }
}
