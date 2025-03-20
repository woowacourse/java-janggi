package domain.route;

import static domain.pattern.Direction.*;

import domain.pattern.Pattern;
import java.util.Map;

public class 차Route extends LinearRoute {

    public 차Route() {
        super(Map.of(
                RIGHT, Pattern.RIGHT,
                DOWN, Pattern.DOWN,
                LEFT, Pattern.LEFT,
                UP, Pattern.UP
        ));
    }
}
