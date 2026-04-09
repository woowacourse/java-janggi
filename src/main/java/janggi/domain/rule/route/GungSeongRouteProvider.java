package janggi.domain.rule.route;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.BACK_LEFT;
import static janggi.domain.rule.route.Direction.BACK_RIGHT;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.FRONT_LEFT;
import static janggi.domain.rule.route.Direction.FRONT_RIGHT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.domain.board.GungSeong;
import java.util.List;
import java.util.Optional;

public class GungSeongRouteProvider extends RouteProvider {

    private static final List<Route> POSSIBLE_ROUTES = List.of(
            Route.from(List.of(FRONT)),
            Route.from(List.of(LEFT)),
            Route.from(List.of(RIGHT)),
            Route.from(List.of(BACK)),
            Route.from(List.of(FRONT_LEFT)),
            Route.from(List.of(FRONT_RIGHT)),
            Route.from(List.of(BACK_LEFT)),
            Route.from(List.of(BACK_RIGHT))
    );

    private final GungSeong gungSeong;

    private GungSeongRouteProvider(GungSeong gungSeong) {
        this.gungSeong = gungSeong;
    }

    public static GungSeongRouteProvider of(GungSeong gungSeong) {
        return new GungSeongRouteProvider(gungSeong);
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        if (gungSeong.contains(to)) {
            return findValidPath(from, to, POSSIBLE_ROUTES);
        }
        return Optional.empty();
    }
}
