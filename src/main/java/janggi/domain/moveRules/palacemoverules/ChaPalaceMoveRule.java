package janggi.domain.moveRules.palacemoverules;

import janggi.domain.Direction;
import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.outofpalacemoverules.ChaMoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChaPalaceMoveRule extends ChaMoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        final List<Position> result = new ArrayList<>();
        final List<Position> availablePositions = super.calculateAvailablePositions(startPosition, team, state);

        if (Palace.isOutOfPalace(startPosition)) {
            return availablePositions;
        }

        final List<Position> availablePalacePositions = calculateAvailablePalacePositions(startPosition, team, state);

        result.addAll(availablePositions);
        result.addAll(availablePalacePositions);

        return result;
    }

    private List<Position> calculateAvailablePalacePositions(Position startPosition, Team team,
                                                             Map<Position, Piece> state) {
        Palace palace = Palace.getPalace(startPosition);
        if (palace != null && palace.isVertexOrCenter(startPosition)) {
            return availablePalacePositions(startPosition, team, state);
        }
        return new ArrayList<>();
    }

    private List<Position> availablePalacePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : Direction.getDiagonalDirections()) {
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
            if (Palace.isOutOfPalace(currentPosition)) {
                break;
            }
            result.add(currentPosition);
            if (state.containsKey(currentPosition)) {
                break;
            }
        }
        return result;
    }
}
