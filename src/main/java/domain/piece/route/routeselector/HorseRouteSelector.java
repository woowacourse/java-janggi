package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.List;

import static domain.MovingPattern.*;

public class HorseRouteSelector implements RouteSelector {

    private static final HorseRouteSelector INSTANCE = new HorseRouteSelector();

    private final List<Route> routes = List.of(
            new Route(UP, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_UP_LEFT),
            new Route(UP, DIAGONAL_UP_LEFT)
    );

    private HorseRouteSelector() {
    }

    public static HorseRouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        return routes.stream()
                .filter(route -> route.isReachableByRoute(origin, destination))
                .findFirst()
                .orElseThrow(InvalidPathException::new);
    }
}
