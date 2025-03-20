package domain.route.linear_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.Map;

public class 차Route extends LinearJanggiPiceRoute {

    public 차Route() {
        super(Map.of(
                RIGHT, Pattern.RIGHT,
                DOWN, Pattern.DOWN,
                LEFT, Pattern.LEFT,
                UP, Pattern.UP
        ));
    }
}
