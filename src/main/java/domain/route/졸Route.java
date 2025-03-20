package domain.route;

import static domain.pattern.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class 졸Route extends LimitedRoute {

    public 졸Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }
}
