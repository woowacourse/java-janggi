package janggi.domain.rule.route;


import static java.lang.Math.min;

import janggi.domain.Intersection;
import janggi.domain.Location;
import janggi.domain.location.Vector;
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
    public List<Location> calculateRoute(Intersection base, Intersection destination) {
        List<Vector> vectors = base.getVectors();
        int maxDistance = calculateMaxDistance(base.getLocation(), destination.getLocation());
        List<Route> moveRoutes = vectors.stream().map(vector ->
                        Route.create(vector.direction(), min(vector.distance(), maxDistance)))
                .toList();

        for (Route route : moveRoutes) {
            List<Location> locations = route.apply(base.getLocation());
            if (locations.contains(destination.getLocation())) {
                return locations;
            }
        }

        throw new RouteResolveException(base.getLocation(), destination.getLocation());
    }

    private int calculateMaxDistance(Location from, Location to) {
        int horizontalDiff = Math.abs(from.calculateHorizontalDiff(to));
        int verticalDiff = Math.abs(from.calculateVerticalDiff(to));
        return Math.max(horizontalDiff, verticalDiff);
    }
}
