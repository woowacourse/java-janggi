package domain.route.limited_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class 마Route extends LimitedJanggiPieceRoute {

    public 마Route() {
        super(Map.of(
                        UP_RIGHT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_RIGHT),
                        RIGHT_UP, List.of(Pattern.RIGHT, Pattern.DIAGONAL_UP_RIGHT),
                        RIGHT_DOWN, List.of(Pattern.RIGHT, Pattern.DIAGONAL_DOWN_RIGHT),
                        DOWN_RIGHT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_RIGHT),
                        DOWN_LEFT, List.of(Pattern.DOWN, Pattern.DIAGONAL_DOWN_LEFT),
                        LEFT_DOWN, List.of(Pattern.LEFT, Pattern.DIAGONAL_DOWN_LEFT),
                        LEFT_UP, List.of(Pattern.LEFT, Pattern.DIAGONAL_UP_LEFT),
                        UP_LEFT, List.of(Pattern.UP, Pattern.DIAGONAL_UP_LEFT)
                ));
    }
}
