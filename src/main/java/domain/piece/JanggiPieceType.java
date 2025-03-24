package domain.piece;

import domain.Pattern;
import domain.piece.movingstrategy.MovingNormalStrategy;
import domain.piece.movingstrategy.JanggiPieceMovingStrategy;
import domain.piece.movingstrategy.Moving포Strategy;
import domain.piece.movingstrategy.NoneMovingStrategy;
import domain.piece.routestrategy.JanggiPieceRouteStrategy;
import domain.piece.routestrategy.LimitedRouteStrategy;
import domain.piece.routestrategy.LinearRouteStrategy;
import domain.piece.routestrategy.NoneRouteStrategy;
import domain.position.JanggiPosition;
import java.util.List;

public enum JanggiPieceType {

    궁(0, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_DOWN),
            List.of(Pattern.MOVE_LEFT),
            List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    마(5, List.of(
            List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    사(3, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_DOWN),
            List.of(Pattern.MOVE_LEFT),
            List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    상(3, List.of(
            List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    졸(2, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_LEFT),
            List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    병(2, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_DOWN),
            List.of(Pattern.MOVE_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    차(13, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_DOWN),
            List.of(Pattern.MOVE_LEFT),
            List.of(Pattern.MOVE_UP)
    ), new LinearRouteStrategy(), new MovingNormalStrategy()),
    포(7, List.of(
            List.of(Pattern.MOVE_RIGHT),
            List.of(Pattern.MOVE_DOWN),
            List.of(Pattern.MOVE_LEFT),
            List.of(Pattern.MOVE_UP)
    ), new LinearRouteStrategy(), new Moving포Strategy()),
    EMPTY(0, List.of(), new NoneRouteStrategy(), new NoneMovingStrategy());

    private final int score;
    private final List<List<Pattern>> routes;
    private final JanggiPieceRouteStrategy routeStrategy;
    private final JanggiPieceMovingStrategy movingStrategy;

    JanggiPieceType(
            int score,
            List<List<Pattern>> routes,
            JanggiPieceRouteStrategy routeStrategy,
            JanggiPieceMovingStrategy movingStrategy
    ) {
        this.score = score;
        this.routes = routes;
        this.routeStrategy = routeStrategy;
        this.movingStrategy = movingStrategy;
    }

    public List<Pattern> getRoute(JanggiPosition origin,
                                  JanggiPosition destination) {
        return routeStrategy.getRoute(routes, origin, destination);
    }

    public void validateCanMove(JanggiSide side, JanggiPiece hurdlePiece, int hurdleCount, JanggiPiece targetPiece) {
        movingStrategy.checkPieceCanMove(side, hurdlePiece, hurdleCount, targetPiece);
    }
}
