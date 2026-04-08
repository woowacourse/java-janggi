package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.Piece;
import janggi.domain.PieceType;
import janggi.domain.Position;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JumpMoveRule implements MoveRule {

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
        return resultPositions;
    }

    private List<Route> maRoutes() {
        Route route1 = new Route(List.of(Direction.NORTH, Direction.NORTH_WEST));
        Route route2 = new Route(List.of(Direction.NORTH, Direction.NORTH_EAST));
        Route route3 = new Route(List.of(Direction.EAST, Direction.NORTH_EAST));
        Route route4 = new Route(List.of(Direction.EAST, Direction.SOUTH_EAST));
        Route route5 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST));
        Route route6 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_WEST));
        Route route7 = new Route(List.of(Direction.WEST, Direction.SOUTH_WEST));
        Route route8 = new Route(List.of(Direction.WEST, Direction.NORTH_WEST));

        return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
    }

    private List<Route> sangRoutes() {
        Route route1 = new Route(List.of(Direction.NORTH, Direction.NORTH_WEST, Direction.NORTH_WEST));
        Route route2 = new Route(List.of(Direction.NORTH, Direction.NORTH_EAST, Direction.NORTH_EAST));
        Route route3 = new Route(List.of(Direction.EAST, Direction.NORTH_EAST, Direction.NORTH_EAST));
        Route route4 = new Route(List.of(Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        Route route5 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_EAST, Direction.SOUTH_EAST));
        Route route6 = new Route(List.of(Direction.SOUTH, Direction.SOUTH_WEST, Direction.SOUTH_WEST));
        Route route7 = new Route(List.of(Direction.WEST, Direction.SOUTH_WEST, Direction.SOUTH_WEST));
        Route route8 = new Route(List.of(Direction.WEST, Direction.NORTH_WEST, Direction.NORTH_WEST));
        return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
    }

    private boolean hasObstacleOnRoute(List<Position> route, Map<Position, Piece> state) {
        Position targetPosition = route.getLast();
        return route.stream()
                .filter(position -> position != targetPosition)
                .anyMatch(state::containsKey);
    }

    private List<Route> getRoutes(Piece piece) {
        if (piece.sameType(PieceType.MA)) {
            return maRoutes();
        }
        return sangRoutes();
    }
}
