package domain.route.limited_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public final class 졸Route extends LimitedJanggiPieceRoute {

    public 졸Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.MOVE_RIGHT),
                LEFT, List.of(Pattern.MOVE_LEFT),
                UP, List.of(Pattern.MOVE_UP)
        ));
    }
}
