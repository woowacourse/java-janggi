package domain.piece.route;

import domain.MovingPattern;
import domain.piece.JanggiSide;
import domain.piece.route.routestrategy.JanggiPieceRouteStrategy;
import domain.piece.route.routestrategy.LimitedRouteStrategy;
import domain.piece.route.routestrategy.LinearRouteStrategy;
import domain.piece.route.routestrategy.NoneRouteStrategy;
import domain.piece.route.routestrategy.PalaceRouteStrategy;
import domain.piece.route.routestrategy.SoldierMovingStrategy;
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
    ), new PalaceRouteStrategy()),
    HORSE_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy()),
    ADVISOR_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT)
    ), new PalaceRouteStrategy()),
    ELEPHANT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy()),
    SOLDIER_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new SoldierMovingStrategy()),
    CHARIOT_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteStrategy()),
    CANNON_ROUTE(List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT)
    ), new LinearRouteStrategy()),
    EMPTY_ROUTE(List.of(), new NoneRouteStrategy())
    ;

    private final List<List<MovingPattern>> routes;
    private final JanggiPieceRouteStrategy strategy;

    JanggiPieceRoute(List<List<MovingPattern>> routes, JanggiPieceRouteStrategy strategy) {
        this.routes = routes;
        this.strategy = strategy;
    }

    public List<MovingPattern> getRoute(JanggiSide side, JanggiPosition origin, JanggiPosition destination) {
        return strategy.getRoute(side, routes, origin, destination);
    }
}
