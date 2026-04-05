package janggi.domain.rule.route;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S6548")
public class StraightRouteProvider extends RouteProvider {

    private static final StraightRouteProvider INSTANCE = new StraightRouteProvider();

    private StraightRouteProvider() {
    }

    public static StraightRouteProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        int maxDistance = calculateMaxDistance(from, to);

        List<Route> possibleRoutes = List.of(
                Route.of(FRONT, maxDistance),
                Route.of(BACK, maxDistance),
                Route.of(LEFT, maxDistance),
                Route.of(RIGHT, maxDistance)
        );

        return findValidPath(from, to, possibleRoutes);
    }

    private int calculateMaxDistance(Location from, Location to) {
        int horizontalDiff = Math.abs(from.calculateHorizontalDiff(to));
        int verticalDiff = Math.abs(from.calculateVerticalDiff(to));
        return Math.max(horizontalDiff, verticalDiff);
    }
}
