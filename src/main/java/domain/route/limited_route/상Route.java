package domain.route.limited_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public final class 상Route extends LimitedJanggiPieceRoute {

    public 상Route() {
        super(Map.of(
                UP_RIGHT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
                RIGHT_UP, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT, Pattern.MOVE_DIAGONAL_UP_RIGHT),
                RIGHT_DOWN, List.of(Pattern.MOVE_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
                DOWN_RIGHT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_RIGHT, Pattern.MOVE_DIAGONAL_DOWN_RIGHT),
                DOWN_LEFT, List.of(Pattern.MOVE_DOWN, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
                LEFT_DOWN, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT, Pattern.MOVE_DIAGONAL_DOWN_LEFT),
                LEFT_UP, List.of(Pattern.MOVE_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT),
                UP_LEFT, List.of(Pattern.MOVE_UP, Pattern.MOVE_DIAGONAL_UP_LEFT, Pattern.MOVE_DIAGONAL_UP_LEFT)
        ));
    }

}
