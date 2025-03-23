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
import java.util.List;
import java.util.Map;

public enum JanggiPieceType {

    궁(0, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    )),
    마(5, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT)
    )),
    사(3, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    )),
    상(3, Map.of(
            UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
            RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
            DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
            LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
            UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT)
    )),
    졸(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    )),
    병(2, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT)
    )),
    차(13, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    )),
    포(7, Map.of(
            RIGHT, List.of(Pattern.MOVE_RIGHT),
            DOWN, List.of(Pattern.MOVE_DOWN),
            LEFT, List.of(Pattern.MOVE_LEFT),
            UP, List.of(Pattern.MOVE_UP)
    )),
    EMPTY(0, Map.of());

    private final int score;
    private final Map<Direction, List<Pattern>> routes;


    JanggiPieceType(int score, Map<Direction, List<Pattern>> routes) {
        this.score = score;
        this.routes = routes;
    }

    public Map<Direction, List<Pattern>> getRoutes() {
        return routes;
    }
}
