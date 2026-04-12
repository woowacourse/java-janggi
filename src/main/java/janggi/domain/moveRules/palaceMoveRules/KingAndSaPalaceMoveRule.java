package janggi.domain.moveRules.palaceMoveRules;

import janggi.domain.Direction;
import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.outOfPalaceMoveRules.OnceMoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KingAndSaPalaceMoveRule extends OnceMoveRule {
    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        List<Direction> directions = Direction.getAllDirections();
        List<Position> availablePositions = calculatePalacePositions(startPosition, directions);
        return filteredPositions(startPosition, availablePositions, state);
    }

    private List<Position> calculatePalacePositions(Position startPosition, List<Direction> directions) {
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : directions) {
            if (startPosition.cannotMoveTo(direction)) {
                continue;
            }
            if (Direction.getDiagonalDirections().contains(direction) && !Palace.isPalaceVertexAndCenterPosition(
                    startPosition)) {
                continue;
            }
            Position movePosition = startPosition.move(direction);
            if (Palace.isOutOfPalace(movePosition)) {
                continue;
            }
            availablePositions.add(movePosition);
        }
        return availablePositions;
    }
}
