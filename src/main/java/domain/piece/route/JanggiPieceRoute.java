package domain.piece.route;

import domain.piece.JanggiSide;
import domain.piece.route.routeselector.*;
import domain.position.JanggiPosition;
import janggiexception.InvalidPathException;

import java.util.ArrayList;
import java.util.List;

public enum JanggiPieceRoute {

    KING_ROUTE(List.of(new InsideOnlyPalaceRouteSelector())),
    HORSE_ROUTE(List.of(new HorseRouteSelector())),
    ADVISOR_ROUTE(List.of(new InsideOnlyPalaceRouteSelector())),
    ELEPHANT_ROUTE(List.of(new ElephantRouteSelector())),
    SOLDIER_ROUTE(List.of(new SoldierRouteSelector(), new PalaceForwardRouteSelector())),
    CHARIOT_ROUTE(List.of(new LinearRouteSelector(), new PalaceLinearRouteSelector())),
    CANNON_ROUTE(List.of(new LinearRouteSelector(), new PalaceLinearRouteSelector())),
    EMPTY_ROUTE(List.of());

    private final List<RouteSelector> routeSelectors;

    JanggiPieceRoute(List<RouteSelector> routeSelectors) {
        this.routeSelectors = routeSelectors;
    }

    public Route getRoute(JanggiSide side, JanggiPosition origin, JanggiPosition destination) {
        List<Route> route = new ArrayList<>();
        routeSelectors.stream()
                .map(selector -> selector.getRoute(side, origin, destination))
                .filter(selectedRoute -> !selectedRoute.isEmpty())
                .forEach(route::add);

        if (route.isEmpty()) {
            throw new InvalidPathException();
        }
        return route.getFirst();
    }
}
