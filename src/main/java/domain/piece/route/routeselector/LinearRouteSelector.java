package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.List;

import static domain.MovingPattern.*;

public class LinearRouteSelector implements RouteSelector {

    public static final LinearRouteSelector INSTANCE = new LinearRouteSelector();
    private final List<Route> movableDirections = List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP)
    );

    private LinearRouteSelector() {
    }

    public static LinearRouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        for (Route route : movableDirections) {
            if (route.isSameDirectionWith(origin, destination)) {
                int moveCount = route.getMoveCount(origin, destination);
                return route.createNewRouteOf(moveCount);
            }
        }
        return Route.createEmptyRoute();
    }
}
