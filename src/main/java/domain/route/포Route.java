package domain.route;

import static domain.pattern.Direction.*;

import domain.pattern.Pattern;
import java.util.Map;

public class 포Route extends LinearRoute {

    public 포Route() {
        super(Map.of(
                RIGHT, Pattern.RIGHT,
                DOWN, Pattern.DOWN,
                LEFT, Pattern.LEFT,
                UP, Pattern.UP
        ));
    }
}
