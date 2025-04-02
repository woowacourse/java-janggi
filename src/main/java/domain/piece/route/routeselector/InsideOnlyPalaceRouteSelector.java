package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.List;

import static domain.MovingPattern.*;

public class InsideOnlyPalaceRouteSelector implements RouteSelector {

    private static final InsideOnlyPalaceRouteSelector INSTANCE = new InsideOnlyPalaceRouteSelector();
    private final List<Route> movableDirectionsInPalace = List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    );

    private InsideOnlyPalaceRouteSelector() {
    }

    public static InsideOnlyPalaceRouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        if (!destination.isPalace()) {
            return Route.createEmptyRoute();
        }

        for (Route route : movableDirectionsInPalace) {
            if (route.isReachableByRoute(origin, destination)) {
                if (route.isDiagonalDirection() && !isMovableDiagonalInPalace(origin, destination)) {
                    return Route.createEmptyRoute();
                }
                return route;
            }
        }
        return Route.createEmptyRoute();
    }

    private boolean isMovableDiagonalInPalace(JanggiPosition origin, JanggiPosition destination) {
        return origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace();
    }
}
