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

public class ZolPalaceMoveRule extends OnceMoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        List<Position> availablePositions = new ArrayList<>(
                super.calculateAvailablePositions(startPosition, team, state));
        List<Direction> diagonalDirections = getPalaceDiagonalDirections(startPosition, team);
        List<Position> diagonalPositions = new ArrayList<>();

        for (Direction direction : diagonalDirections) {
            if (startPosition.cannotMoveTo(direction)) {
                continue;
            }
            Position movePosition = startPosition.move(direction);
            if (!Palace.isOutOfPalace(movePosition)) {
                diagonalPositions.add(movePosition);
            }
        }
        availablePositions.addAll(filteredPositions(startPosition, diagonalPositions, state));

        return availablePositions;
    }

    private List<Direction> getPalaceDiagonalDirections(Position startPosition, Team team) {
        if (!Palace.isPalaceVertexAndCenterPosition(startPosition)) {
            return new ArrayList<>();
        }

        if (team.isSameTeam(Team.CHO)) {
            if (Palace.isInHanPalaceVertex(startPosition) || startPosition.equals(Palace.HAN_PALACE_CENTER)) {
                return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
            }
        }
        if (team.isSameTeam(Team.HAN)) {
            if (Palace.isInChoPalaceVertex(startPosition) || startPosition.equals(Palace.CHO_PALACE_CENTER)) {
                return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
            }
        }

        return new ArrayList<>();
    }
}
