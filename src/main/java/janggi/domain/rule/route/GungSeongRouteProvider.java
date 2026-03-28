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
import janggi.domain.piece.PieceType;
import java.util.List;

@SuppressWarnings("java:S6548")
public class GungSeongRouteProvider implements RouteProvider {

    private static final GungSeongRouteProvider INSTANCE = new GungSeongRouteProvider();
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

    private GungSeongRouteProvider() {
    }

    public static GungSeongRouteProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Location> calculateRoute(PieceType pieceType, Location from, Location to) {
        return RouteProvider.findValidPath(pieceType, from, to, POSSIBLE_ROUTES);
    }
}
