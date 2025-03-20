package domain.route.limited_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public class 사Route extends LimitedJanggiPieceRoute {

    public 사Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.RIGHT),
                DOWN, List.of(Pattern.DOWN),
                LEFT, List.of(Pattern.LEFT),
                UP, List.of(Pattern.UP)
        ));
    }
}
