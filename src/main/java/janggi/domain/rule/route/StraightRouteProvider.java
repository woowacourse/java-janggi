package janggi.domain.rule.route;

import static janggi.domain.rule.route.Direction.BACK;
import static janggi.domain.rule.route.Direction.FRONT;
import static janggi.domain.rule.route.Direction.LEFT;
import static janggi.domain.rule.route.Direction.RIGHT;

import janggi.domain.Location;
import janggi.exception.RouteResolveException;
import java.util.List;

public class StraightRouteProvider implements RouteProvider {

    private static final StraightRouteProvider INSTANCE = new StraightRouteProvider();

    private StraightRouteProvider() {
    }

    public static StraightRouteProvider getInstance() {
        return INSTANCE;
    }

    @Override
    public List<Location> calculateRoute(Location from, Location to) {
        int maxDistance = calculateMaxDistance(from, to);

        List<Route> directions = List.of(
                Route.create(FRONT, maxDistance),
                Route.create(BACK, maxDistance),
                Route.create(LEFT, maxDistance),
                Route.create(RIGHT, maxDistance)
        );

        for (Route route : directions) {
            List<Location> locations = route.apply(from);
            if (locations.contains(to)) {
                return locations;
            }
        }

        throw new RouteResolveException(from, to);
    }

    private int calculateMaxDistance(Location from, Location to) {
        int horizontalDiff = Math.abs(from.calculateHorizontalDiff(to));
        int verticalDiff = Math.abs(from.calculateVerticalDiff(to));
        return Math.max(horizontalDiff, verticalDiff);
    }
}
