package janggi.domain.moveRules;

import janggi.domain.Direction;
import janggi.domain.MoveRule;
import janggi.domain.Route;
import janggi.domain.Team;
import java.util.List;

public class PoMoveRule implements MoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        Route route1 = new Route(List.of(Direction.UP));
        Route route2 = new Route(List.of(Direction.RIGHT));
        Route route3 = new Route(List.of(Direction.DOWN));
        Route route4 = new Route(List.of(Direction.LEFT));
        return List.of(route1, route2, route3, route4);
    }
}
