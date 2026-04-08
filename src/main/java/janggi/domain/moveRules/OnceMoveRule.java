package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class OnceMoveRule implements MoveRule {

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        Piece piece = state.get(startPosition);
        List<Direction> directions = findDirections(piece, team);
        int currentColumn = startPosition.getColumn();
        int currentRow = startPosition.getRow();
        List<Position> result = new ArrayList<>();
        for (Direction direction : directions) {
            int newColumn = currentColumn + direction.getColumn();
            int newRaw = currentRow + direction.getRow();
            if (Position.isInsideBoundary(newColumn, newRaw)) {
                Position movePosition = new Position(newColumn, newRaw);
                result.add(movePosition);
            }
        }
        return result;
    }

    private List<Direction> choZolRoutes() {
        return List.of(Direction.NORTH, Direction.EAST, Direction.WEST);
    }

    private List<Direction> hanZolRoutes() {
        return List.of(Direction.SOUTH, Direction.EAST, Direction.WEST);
    }

    private List<Direction> palaceRoutes() {

        return Arrays.stream(Direction.values()).toList();
    }

    private List<Direction> findDirections(Piece piece, Team team) {
        if (piece.sameType(PieceType.ZOL)) {
            if (team.isSameTeam(Team.CHO)) {
                return choZolRoutes();
            }
            return hanZolRoutes();
        }
        return palaceRoutes();
    }
}
