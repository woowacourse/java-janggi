package janggi.domain.piece.moverules;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.List;

public class SaMoveRule extends GeneralMoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        Route route5 = new Route(List.of(Direction.UP_LEFT));
        Route route6 = new Route(List.of(Direction.UP_RIGHT));
        Route route7 = new Route(List.of(Direction.DOWN_LEFT));
        Route route8 = new Route(List.of(Direction.DOWN_RIGHT));
        return List.of(route1, route2, route3, route4, route5, route6, route7, route8);
    }
}
