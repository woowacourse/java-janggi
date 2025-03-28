package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.List;

public class LimitedRouteSelector implements RouteSelector {

    @Override
    public Route getRoute(final JanggiSide side, final List<Route> routes, final JanggiPosition origin, final JanggiPosition destination) {
        return routes.stream()
                .filter(route -> route.isReachableByRoute(origin, destination))
                .findFirst()
                .orElseThrow(InvalidPathException::new);
    }
}
