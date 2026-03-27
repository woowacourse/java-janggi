package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.MoveRule;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.List;

public class KingMoveRule implements MoveRule {

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
