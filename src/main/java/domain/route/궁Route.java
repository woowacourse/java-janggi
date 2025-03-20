package domain.route;

import static domain.pattern.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class 궁Route extends LimitedRoute {

    public 궁Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }
}
