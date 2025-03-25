package domain.piece.route;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.piece.route.routeselector.JanggiPieceRouteSelector;
import domain.piece.route.routeselector.LimitedRouteSelector;
import domain.piece.route.routeselector.LinearRouteSelector;
import domain.piece.route.routeselector.NoneRouteSelector;
import domain.piece.route.routeselector.PalaceRouteSelector;
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
    ), new PalaceRouteSelector()),
    HORSE_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector()),
    ADVISOR_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT)
    ), new PalaceRouteSelector()),
    ELEPHANT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteSelector()),
    SOLDIER_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new SoldierRouteSelector()),
    CHARIOT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector()),
    CANNON_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteSelector()),
    EMPTY_ROUTE(List.of(), new NoneRouteSelector())
    ;

    private final List<List<MovingPattern>> routes;
    private final JanggiPieceRouteSelector strategy;

    JanggiPieceRoute(List<List<MovingPattern>> routes, JanggiPieceRouteSelector strategy) {
        this.routes = routes;
        this.strategy = strategy;
    }

    public List<MovingPattern> getRoute(JanggiSide side, JanggiPosition origin, JanggiPosition destination) {
        return strategy.getRoute(side, routes, origin, destination);
    }
}
