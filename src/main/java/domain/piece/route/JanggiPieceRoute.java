package domain.piece.route;

import domain.piece.JanggiSide;
import domain.piece.route.palacerule.*;
import domain.piece.route.routeselector.*;
import domain.position.JanggiPosition;

import java.util.List;

import static domain.MovingPattern.*;

public enum JanggiPieceRoute {

    KING_ROUTE(List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_UP_RIGHT)
    ), new LimitedRouteSelector(), new InsideOnlyPalaceMovementRule()),
    HORSE_ROUTE(List.of(
            new Route(UP, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_UP_LEFT),
            new Route(UP, DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector(), new PalaceNoOperationRule()),
    ADVISOR_ROUTE(List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_UP_RIGHT)
    ), new LimitedRouteSelector(), new InsideOnlyPalaceMovementRule()),
    ELEPHANT_ROUTE(List.of(
            new Route(UP, DIAGONAL_UP_RIGHT, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_UP_RIGHT, DIAGONAL_UP_RIGHT),
            new Route(RIGHT, DIAGONAL_DOWN_RIGHT, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_RIGHT, DIAGONAL_DOWN_RIGHT),
            new Route(DOWN, DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_DOWN_LEFT, DIAGONAL_DOWN_LEFT),
            new Route(LEFT, DIAGONAL_UP_LEFT, DIAGONAL_UP_LEFT),
            new Route(UP, DIAGONAL_UP_LEFT, DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector(), new PalaceNoOperationRule()),
    SOLDIER_ROUTE(List.of(
            new Route(RIGHT),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DOWN),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    ), new SoldierRouteSelector(), new PalaceForwardMovementRule()),
    CHARIOT_ROUTE(List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector(), new PalaceLinearMovementRule()),
    CANNON_ROUTE(List.of(
            new Route(RIGHT),
            new Route(DOWN),
            new Route(LEFT),
            new Route(UP),
            new Route(DIAGONAL_UP_RIGHT),
            new Route(DIAGONAL_UP_LEFT),
            new Route(DIAGONAL_DOWN_LEFT),
            new Route(DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector(), new PalaceLinearMovementRule()),
    EMPTY_ROUTE(List.of(), new NoneRouteSelector(), new PalaceNoOperationRule());

    private final List<Route> routes;
    private final RouteSelector routeSelector;
    private final PalaceMovementRule palaceRule;

    JanggiPieceRoute(List<Route> routes, RouteSelector routeSelector, PalaceMovementRule palaceRule) {
        this.routes = routes;
        this.routeSelector = routeSelector;
        this.palaceRule = palaceRule;
    }

    public Route getRoute(JanggiSide side, JanggiPosition origin, JanggiPosition destination) {
        Route route = routeSelector.getRoute(side, routes, origin, destination);
        palaceRule.validateCanMoveInPalace(route, origin, destination);
        return route;
    }
}
