package janggi.domain.moveRules.palacemoverules;

import janggi.domain.Direction;
import janggi.domain.Palace;
import janggi.domain.Piece;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.outofpalacemoverules.OnceMoveRule;
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
        Palace currentPalace = Palace.getPalace(startPosition);
        if (currentPalace == null || !currentPalace.isVertexOrCenter(startPosition)) {
            return new ArrayList<>();
        }

        if (team.isSameTeam(Team.CHO) && currentPalace == Palace.HAN) {
            return List.of(Direction.NORTH_WEST, Direction.NORTH_EAST);
        }
        if (team.isSameTeam(Team.HAN) && currentPalace == Palace.CHO) {
            return List.of(Direction.SOUTH_WEST, Direction.SOUTH_EAST);
        }

        return new ArrayList<>();
    }
}
