package domain.route;

import static domain.pattern.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class 병Route extends LimitedRoute {

    public 병Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT)
        ));
    }
}
