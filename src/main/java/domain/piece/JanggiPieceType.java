package domain.piece;

import static domain.Direction.DOWN;
import static domain.Direction.DOWN_LEFT;
import static domain.Direction.DOWN_RIGHT;
import static domain.Direction.LEFT;
import static domain.Direction.LEFT_DOWN;
import static domain.Direction.LEFT_UP;
import static domain.Direction.RIGHT;
import static domain.Direction.RIGHT_DOWN;
import static domain.Direction.RIGHT_UP;
import static domain.Direction.UP;
import static domain.Direction.UP_LEFT;
import static domain.Direction.UP_RIGHT;

import domain.Pattern;
import domain.Direction;
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
import java.util.Map;

public enum JanggiPieceType {

    궁(0, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    마(5, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    사(3, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    상(3, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    졸(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    병(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT)
    ), new LimitedRouteStrategy(), new MovingNormalStrategy()),
    차(13, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LinearRouteStrategy(), new MovingNormalStrategy()),
    포(7, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LinearRouteStrategy(), new Moving포Strategy()),
    EMPTY(0, Map.of(), new NoneRouteStrategy(), new NoneMovingStrategy());

    private final int score;
    private final Map<Direction, List<Pattern>> routes;
    private final JanggiPieceRouteStrategy routeStrategy;
    private final JanggiPieceMovingStrategy movingStrategy;

    JanggiPieceType(
            int score,
            Map<Direction,
            List<Pattern>> routes,
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
