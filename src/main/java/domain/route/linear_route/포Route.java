package domain.route.linear_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.Map;

public final class 포Route extends LinearJanggiPieceRoute {

    public 포Route() {
        super(Map.of(
                RIGHT, Pattern.RIGHT,
                DOWN, Pattern.DOWN,
                LEFT, Pattern.LEFT,
                UP, Pattern.UP
        ));
    }
}
