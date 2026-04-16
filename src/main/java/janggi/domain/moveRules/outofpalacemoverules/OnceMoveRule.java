package janggi.domain.moveRules.outofpalacemoverules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OnceMoveRule implements MoveRule {

    private static final List<Direction> CHO_ZOL_ROUTES = List.of(
            Direction.NORTH, Direction.EAST, Direction.WEST
    );
    private static final List<Direction> HAN_ZOL_ROUTES = List.of(
            Direction.SOUTH, Direction.EAST, Direction.WEST
    );
    protected static final List<Direction> PALACE_ROUTES = Direction.getAllDirections();

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        Piece piece = state.get(startPosition);
        List<Direction> directions = findDirections(piece, team);
        List<Position> availablePositions = new ArrayList<>();
        for (Direction direction : directions) {
            if (startPosition.cannotMoveTo(direction)) {
                continue;
            }
            Position movePosition = startPosition.move(direction);
            availablePositions.add(movePosition);
        }
        return filteredPositions(startPosition, availablePositions, state);
    }

    private List<Direction> findDirections(Piece piece, Team team) {
        if (piece.sameType(PieceType.ZOL)) {
            if (team.isSameTeam(Team.CHO)) {
                return CHO_ZOL_ROUTES;
            }
            return HAN_ZOL_ROUTES;
        }
        return PALACE_ROUTES;
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
