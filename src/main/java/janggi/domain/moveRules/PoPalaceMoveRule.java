package janggi.domain.moveRules;

import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PoPalaceMoveRule extends PoMoveRule {

    @Override
    public List<Position> calculateAvailablePositions(
            Position position,
            Team team,
            Map<Position, Piece> state) {
        final List<Position> result = new ArrayList<>();
        final List<Position> availablePositions = super.calculateAvailablePositions(position, team, state);

        if (!Palace.isInPalace(position)) {
            return availablePositions;
        }

        final List<Position> availablePalacePositions = calculateAvailablePalacePositions(position, team, state);

        result.addAll(availablePositions);
        result.addAll(availablePalacePositions);

        return result;
    }

    private List<Position> calculateAvailablePalacePositions(
            Position position,
            Team team,
            Map<Position, Piece> state) {
        if (Palace.isPoInChoPalaceDigonal(position)) {
            Position choCenter = Palace.CHO_PALACE_CENTER;
            return availablePalacePositions(position, team, state, choCenter);
        }
        Position hanCenter = Palace.HAN_PALACE_CENTER;
        return availablePalacePositions(position, team, state, hanCenter);
    }

    private List<Position> availablePalacePositions(Position position, Team team, Map<Position, Piece> state,
                                                    Position center) {
        List<Position> availablePositions = new ArrayList<>();
        if (!state.containsKey(center)) {
            return availablePositions;
        }
        if (state.get(center).isPo()) {
            return availablePositions;
        }
        Position diagonal = Palace.calculateOppositePalaceDiagonalPosition(position);
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
