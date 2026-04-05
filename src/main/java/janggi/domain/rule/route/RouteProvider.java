package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import java.util.Optional;

public abstract class RouteProvider {

    public abstract Optional<List<Location>> calculateRoute(Location from, Location to);

    protected Optional<List<Location>> findValidPath(Location from, Location to, List<Route> possibleRoutes) {
        for (Route route : possibleRoutes) {
            List<Location> locationsOnPath = route.calculateLocationsOnPath(from);
            Location expectedDestination = locationsOnPath.getLast();
            if (expectedDestination.equals(to)) {
                return Optional.of(locationsOnPath);
            }
        }
        return Optional.empty();
    }
}
