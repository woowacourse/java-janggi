package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ChaMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : getStraightDirections()) {
            availablePositions.addAll(findPositionsByDirection(startPosition, direction, state));
        }

        return availablePositions;
    }

    private List<Position> findPositionsByDirection(Position startPosition, Direction direction,
                                                    Map<Position, Piece> state) {
        List<Position> result = new ArrayList<>();
        int currentColumn = startPosition.getColumn() + direction.getColumn();
        int currentRow = startPosition.getRow() + direction.getRow();

        while (Position.isInsideBoundary(currentColumn, currentRow)) {
            Position movePosition = new Position(currentColumn, currentRow);
            result.add(movePosition);
            if (state.containsKey(movePosition)) {
                break;
            }
            currentColumn += direction.getColumn();
            currentRow += direction.getRow();
        }

        return result;
    }

    private List<Direction> getStraightDirections() {
        return Arrays.stream(Direction.values())
                .filter(Direction::isStraight)
                .toList();
    }
}
