package domain.piece.route;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.piece.route.palacerule.InsideOnlyPalaceMovementRule;
import domain.piece.route.palacerule.PalaceForwardMovementRule;
import domain.piece.route.palacerule.PalaceLinearMovementRule;
import domain.piece.route.palacerule.PalaceMovementRule;
import domain.piece.route.palacerule.PalaceNoOperationRule;
import domain.piece.route.routeselector.JanggiPieceRouteSelector;
import domain.piece.route.routeselector.LimitedRouteSelector;
import domain.piece.route.routeselector.LinearRouteSelector;
import domain.piece.route.routeselector.NoneRouteSelector;
import domain.piece.route.routeselector.SoldierRouteSelector;
import domain.position.JanggiPosition;
import java.util.List;

public enum JanggiPieceRoute {

    KING_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT)
    ), new LimitedRouteSelector(), new InsideOnlyPalaceMovementRule()),
    HORSE_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector(), new PalaceNoOperationRule()),
    ADVISOR_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT)
    ), new LimitedRouteSelector(), new InsideOnlyPalaceMovementRule()),
    ELEPHANT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector(), new PalaceNoOperationRule()),
    SOLDIER_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new SoldierRouteSelector(), new PalaceForwardMovementRule()),
    CHARIOT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector(), new PalaceLinearMovementRule()),
    CANNON_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector(), new PalaceLinearMovementRule()),
    EMPTY_ROUTE(List.of(), new NoneRouteSelector(), new PalaceNoOperationRule())
    ;

    private final List<List<MovingPattern>> routes;
    private final JanggiPieceRouteSelector routeSelector;
    private final PalaceMovementRule palaceRule;

    JanggiPieceRoute(List<List<MovingPattern>> routes, JanggiPieceRouteSelector routeSelector, PalaceMovementRule palaceRule) {
        this.routes = routes;
        this.routeSelector = routeSelector;
        this.palaceRule = palaceRule;
    }

    public List<MovingPattern> getRoute(JanggiSide side, JanggiPosition origin, JanggiPosition destination) {
        List<MovingPattern> route = routeSelector.getRoute(side, routes, origin, destination);
        palaceRule.validateCanMove(route, origin, destination);
        return route;
    }
}
