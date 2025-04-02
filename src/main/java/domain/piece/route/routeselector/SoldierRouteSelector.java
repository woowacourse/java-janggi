package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.ArrayList;
import java.util.List;

import static domain.MovingPattern.*;

public class SoldierRouteSelector implements RouteSelector {

    private static final SoldierRouteSelector INSTANCE = new SoldierRouteSelector();

    private static final List<Route> SOLDIER_OF_CHO_DIRECTIONS = List.of(
            new Route(RIGHT),
            new Route(LEFT),
            new Route(UP)
    );
    private static final List<Route> SOLDIER_OF_HAN_DIRECTIONS = List.of(
            new Route(RIGHT),
            new Route(LEFT),
            new Route(DOWN)
    );

    private SoldierRouteSelector() {
    }

    public static SoldierRouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        List<Route> routes = new ArrayList<>();
        if (side == JanggiSide.CHO) {
            routes = SOLDIER_OF_CHO_DIRECTIONS;
        }
        if (side == JanggiSide.HAN) {
            routes = SOLDIER_OF_HAN_DIRECTIONS;
        }

        return routes.stream()
                .filter(route -> route.isReachableByRoute(origin, destination))
                .findFirst()
                .orElse(Route.createEmptyRoute());
    }
}
