package janggi.domain.piece.moverules;

import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import janggi.domain.common.Team;
import janggi.domain.route.Route;
import java.util.ArrayList;
import java.util.List;

public class ZolMoveRule extends GeneralMoveRule {

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

    @Override
    protected List<Route> addPalaceRoutes(Position position, Team team) {
        List<Route> routes = super.addPalaceRoutes(position, team);

        List<Route> forwardRoutes = new ArrayList<>();

        for (Route route : routes) {
            judgeForwardDiagonal(team, route, forwardRoutes);
        }

        return forwardRoutes;
    }

    private void judgeForwardDiagonal(Team team, Route route, List<Route> forwardRoutes) {
        if (isForwardDiagonal(route, team)) {
            forwardRoutes.add(route);
        }
    }

    private boolean isForwardDiagonal(Route route, Team team) {
        if (team == Team.CHO) {
            return route.isUpDiagonal();
        }
        return route.isDownDiagonal();
    }
}
