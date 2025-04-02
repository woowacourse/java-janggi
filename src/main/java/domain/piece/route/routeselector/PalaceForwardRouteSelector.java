package domain.piece.route.routeselector;

import domain.piece.JanggiSide;
import domain.piece.route.Route;
import domain.position.JanggiPosition;

import java.util.List;

import static domain.MovingPattern.*;

public class PalaceForwardRouteSelector implements RouteSelector {

    private static final PalaceForwardRouteSelector INSTANCE = new PalaceForwardRouteSelector();

    private final List<Route> movableDirectionsOfChoInPalace = List.of(
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT)
    );

    private final List<Route> movableDirectionsOfHanInPalace = List.of(
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    );

    private PalaceForwardRouteSelector() {
    }

    public static PalaceForwardRouteSelector getInstance() {
        return INSTANCE;
    }

    @Override
    public Route getRoute(final JanggiSide side, final JanggiPosition origin, final JanggiPosition destination) {
        if (!isMoveInPalace(origin, destination)) {
            return Route.createEmptyRoute();
        }

        if (side == JanggiSide.CHO) {
            for (Route route : movableDirectionsOfChoInPalace) {
                if (route.isSameDirectionWith(origin, destination)) {
                    return route;
                }
            }
        }

        if (side == JanggiSide.HAN) {
            for (Route route : movableDirectionsOfHanInPalace) {
                if (route.isSameDirectionWith(origin, destination)) {
                    return route;
                }
            }
        }

        return Route.createEmptyRoute();
    }

    private boolean isMoveInPalace(JanggiPosition origin, JanggiPosition destination) {
        return origin.isDiagonalMovablePalace() && destination.isDiagonalMovablePalace();
    }
}
