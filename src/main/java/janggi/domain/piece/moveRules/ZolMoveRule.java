package janggi.domain.piece.moveRules;

import janggi.domain.common.Direction;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.List;

public class ZolMoveRule implements MoveRule {

    @Override
    public List<Route> findRoutes(Team team) {
        if (team == Team.CHO) {
            Route route1 = new Route(List.of(Direction.UP));
            Route route2 = new Route(List.of(Direction.LEFT));
            Route route3 = new Route(List.of(Direction.RIGHT));
            return List.of(route1, route2, route3);
        }
        Route route1 = new Route(List.of(Direction.DOWN));
        Route route2 = new Route(List.of(Direction.LEFT));
        Route route3 = new Route(List.of(Direction.RIGHT));
        return List.of(route1, route2, route3);
    }

}
