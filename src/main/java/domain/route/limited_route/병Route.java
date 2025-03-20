package domain.route.limited_route;

import static domain.route.Direction.*;

import domain.pattern.Pattern;
import java.util.List;
import java.util.Map;

public final class 병Route extends LimitedJanggiPieceRoute {

    public 병Route() {
        super(Map.of(
                RIGHT, List.of(Pattern.MOVE_RIGHT),
                DOWN, List.of(Pattern.MOVE_DOWN),
                LEFT, List.of(Pattern.MOVE_LEFT)
        ));
    }
}
