package domain.piece;

import domain.MovingPattern;
import domain.piece.movingstrategy.MovingNormalStrategy;
import domain.piece.movingstrategy.JanggiPieceMovingStrategy;
import domain.piece.movingstrategy.MovingCannonStrategy;
import domain.piece.movingstrategy.NoneMovingStrategy;
import domain.piece.routestrategy.JanggiPieceRouteStrategy;
import domain.piece.routestrategy.LimitedRouteStrategy;
import domain.piece.routestrategy.LinearRouteStrategy;
import domain.piece.routestrategy.NoneRouteStrategy;
import domain.position.JanggiPosition;
import java.util.List;

public enum JanggiPieceType {

    KING(0, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    HORSE(5, List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    ADVISOR(3, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    ELEPHANT(3, List.of(
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT, MovingPattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(MovingPattern.MOVE_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT, MovingPattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(MovingPattern.MOVE_DOWN, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT, MovingPattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(MovingPattern.MOVE_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(MovingPattern.MOVE_UP, MovingPattern.MOVE_DIAGONAL_UP_LEFT, MovingPattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    SOLDIER_OF_CHO(2, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    SOLDIER_OF_HAN(2, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    CHARIOT(13, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP)
    ), new LinearRouteStrategy(), new MovingNormalStrategy()),
    CANNON(7, List.of(
            List.of(MovingPattern.MOVE_RIGHT),
            List.of(MovingPattern.MOVE_DOWN),
            List.of(MovingPattern.MOVE_LEFT),
            List.of(MovingPattern.MOVE_UP)
    ), new LinearRouteStrategy(), new MovingCannonStrategy()),
    EMPTY(0, List.of(), new NoneRouteStrategy(), new NoneMovingStrategy());

    private final int score;
    private final List<List<MovingPattern>> routes;
    private final JanggiPieceRouteStrategy routeStrategy;
    private final JanggiPieceMovingStrategy movingStrategy;

    JanggiPieceType(
            int score,
            List<List<MovingPattern>> routes,
            JanggiPieceRouteStrategy routeStrategy,
            JanggiPieceMovingStrategy movingStrategy
    ) {
        this.score = score;
        this.routes = routes;
        this.routeStrategy = routeStrategy;
        this.movingStrategy = movingStrategy;
    }

    public List<MovingPattern> getRoute(JanggiPosition origin,
                                        JanggiPosition destination) {
        return routeStrategy.getRoute(routes, origin, destination);
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movingStrategy.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }
}
