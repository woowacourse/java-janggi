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
import java.util.List;

@SuppressWarnings("java:S6548")
public class GungSeongRouteProvider implements RouteProvider {

    private static final GungSeongRouteProvider INSTANCE = new GungSeongRouteProvider();

    private GungSeongRouteProvider() {}

    public static GungSeongRouteProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        List<Route> directions = List.of(
                Route.of(List.of(FRONT)),
                Route.of(List.of(LEFT)),
                Route.of(List.of(RIGHT)),
                Route.of(List.of(BACK)),
                Route.of(List.of(FRONT_LEFT)),
                Route.of(List.of(FRONT_RIGHT)),
                Route.of(List.of(BACK_LEFT)),
                Route.of(List.of(BACK_RIGHT))
        );

        for (Route route : directions) {
            List<Location> locations = route.apply(from);
            if (locations.getLast().equals(to)) {
                return locations;
            }
        }

        throw new IllegalArgumentException("해당 위치에 도달할 수 없습니다.");
    }
}
