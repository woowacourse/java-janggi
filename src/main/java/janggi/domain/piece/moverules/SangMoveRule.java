package janggi.domain.piece.moverules;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.List;

public class SangMoveRule extends GeneralMoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP, Direction.UP_LEFT, Direction.UP_LEFT));
        Route route2 = new Route(List.of(Direction.UP, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route3 = new Route(List.of(Direction.RIGHT, Direction.UP_RIGHT, Direction.UP_RIGHT));
        Route route4 = new Route(List.of(Direction.RIGHT, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route5 = new Route(List.of(Direction.DOWN, Direction.DOWN_RIGHT, Direction.DOWN_RIGHT));
        Route route6 = new Route(List.of(Direction.DOWN, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route7 = new Route(List.of(Direction.LEFT, Direction.DOWN_LEFT, Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.LEFT, Direction.UP_LEFT, Direction.UP_LEFT));
        return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
    }
}
