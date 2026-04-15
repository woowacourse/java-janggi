package janggi.domain.moveRules.palacemoverules;

import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.outofpalacemoverules.PoMoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PoPalaceMoveRule extends PoMoveRule {

    @Override
    public List<Position> calculateAvailablePositions(
            Position startPosition,
            Team team,
            Map<Position, Piece> state) {
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

    private List<Position> calculateAvailablePalacePositions(
            Position position,
            Team team,
            Map<Position, Piece> state) {
        Palace palace = Palace.getPalace(position);
        if (palace != null && palace.isVertex(position)) {
            return availablePalacePositions(position, team, state, palace);
        }
        return new ArrayList<>();
    }

    private List<Position> availablePalacePositions(Position position, Team team, Map<Position, Piece> state,
                                                    Palace palace) {
        List<Position> availablePositions = new ArrayList<>();
        Position center = palace.getCenter();
        if (!state.containsKey(center)) {
            return availablePositions;
        }
        if (state.get(center).isPo()) {
            return availablePositions;
        }
        Position diagonal = palace.calculateOppositeVertex(position);
        if (state.containsKey(diagonal) && !state.get(diagonal).isEnemy(team)) {
            return availablePositions;
        }
        if (state.containsKey(diagonal) && state.get(diagonal).isPo()) {
            return availablePositions;
        }
        availablePositions.add(diagonal);
        return availablePositions;
    }
}
