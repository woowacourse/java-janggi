package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChaMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : Direction.getStraightDirections()) {
            availablePositions.addAll(findPositionsByDirection(startPosition, direction, state));
        }
        return filteredPositions(startPosition, availablePositions, state);
    }

    private List<Position> findPositionsByDirection(Position startPosition, Direction direction,
                                                    Map<Position, Piece> state) {
        List<Position> result = new ArrayList<>();
        Position currentPosition = startPosition;
        while (!currentPosition.cannotMoveTo(direction)) {
            currentPosition = currentPosition.move(direction);
            result.add(currentPosition);
            if (state.containsKey(currentPosition)) {
                break;
            }
        }
        return result;
    }

    protected List<Position> filteredPositions(Position position, List<Position> availablePositions,
                                               Map<Position, Piece> state) {
        Piece currentPiece = state.get(position);
        return availablePositions.stream()
                .filter(targetPosition -> {
                    Piece targetPiece = state.get(targetPosition);
                    return targetPiece == null || targetPiece.isEnemy(currentPiece.getTeam());
                })
                .toList();
    }
}
