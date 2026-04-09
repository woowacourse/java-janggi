package janggi.domain.piece.moverules;

import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChaMoveRule extends GeneralMoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        return List.of(route1, route2, route3, route4);
    }

    @Override
    protected Map<Position, List<Position>> convertToPositions(Position position, List<Route> routes) {
        Map<Position, List<Position>> result = new HashMap<>();
        for (Route route : routes) {
            route.applyContinuousDirections(position, result);
        }
        return result;
    }
}
