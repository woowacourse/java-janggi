package domain.route.linear_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.Map;

public final class 차Route extends LinearJanggiPieceRoute {

    public 차Route() {
        super(Map.of(
                RIGHT, Pattern.MOVE_RIGHT,
                DOWN, Pattern.MOVE_DOWN,
                LEFT, Pattern.MOVE_LEFT,
                UP, Pattern.MOVE_UP
        ));
    }
}
