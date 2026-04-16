package janggi.domain.moveRules.outofpalacemoverules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import janggi.domain.moveRules.MoveRule;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JumpMoveRule implements MoveRule {

    private static final List<Route> MA_ROUTES = List.of(
            new Route(List.of(Direction.NORTH, Direction.NORTH_WEST)),
            new Route(List.of(Direction.NORTH, Direction.NORTH_EAST)),
            new Route(List.of(Direction.EAST, Direction.NORTH_EAST)),
            new Route(List.of(Direction.EAST, Direction.SOUTH_EAST)),
            new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST)),
            new Route(List.of(Direction.SOUTH, Direction.SOUTH_WEST)),
            new Route(List.of(Direction.WEST, Direction.SOUTH_WEST)),
            new Route(List.of(Direction.WEST, Direction.NORTH_WEST))
    );
    private static final List<Route> SANG_ROUTES = List.of(
            new Route(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST)),
            new Route(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new Route(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST)),
            new Route(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST)),
            new Route(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST)),
            new Route(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST)),
            new Route(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST))
    );

    @Override
    public List<Position> calculateAvailablePositions(Position startPosition, Team team, Map<Position, Piece> state) {
        Piece piece = state.get(startPosition);
        List<Route> routes = getRoutes(piece);
        List<Position> resultPositions = new ArrayList<>();
        for (Route route : routes) {
            List<Position> routeToPositions = route.convertToPositions(startPosition);
            if (!routeToPositions.isEmpty() && !hasObstacleOnRoute(routeToPositions, state)) {
                resultPositions.add(routeToPositions.getLast());
            }
        }
        return filteredPositions(startPosition, resultPositions, state);
    }

    private boolean hasObstacleOnRoute(List<Position> route, Map<Position, Piece> state) {
        Position targetPosition = route.getLast();
        return route.stream()
                .filter(position -> position != targetPosition)
                .anyMatch(state::containsKey);
    }

    private List<Route> getRoutes(Piece piece) {
        if (piece.sameType(PieceType.MA)) {
            return MA_ROUTES;
        }
        return SANG_ROUTES;
    }

    private List<Position> filteredPositions(Position position, List<Position> availablePositions,
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
