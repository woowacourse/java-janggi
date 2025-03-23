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
import domain.piece.movingstrategy.JanggiPieceMovingStrategy;
import domain.piece.movingstrategy.LimitedMovingStrategy;
import domain.piece.movingstrategy.LinearMovingStrategy;
import domain.piece.movingstrategy.NoneMovingStrategy;
import domain.position.JanggiPosition;
import java.util.List;
import java.util.Map;

public enum JanggiPieceType {

    궁(0, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedMovingStrategy()),
    마(5, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedMovingStrategy()),
    사(3, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedMovingStrategy()),
    상(3, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT)
    ), new LimitedMovingStrategy()),
    졸(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LimitedMovingStrategy()),
    병(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT)
    ), new LimitedMovingStrategy()),
    차(13, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LinearMovingStrategy()),
    포(7, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    ), new LinearMovingStrategy()),
    EMPTY(0, Map.of(), new NoneMovingStrategy());

    private final int score;
    private final Map<Direction, List<Pattern>> routes;
    private final JanggiPieceMovingStrategy movingStrategy;

    JanggiPieceType(int score, Map<Direction, List<Pattern>> routes, JanggiPieceMovingStrategy movingStrategy) {
        this.score = score;
        this.routes = routes;
        this.movingStrategy = movingStrategy;
    }

    public List<Pattern> getRoute(JanggiPosition origin,
                                  JanggiPosition destination) {
        return movingStrategy.getRoute(routes, origin, destination);
    }
}
