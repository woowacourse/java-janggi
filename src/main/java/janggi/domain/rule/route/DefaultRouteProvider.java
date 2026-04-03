package janggi.domain.rule.route;

import janggi.domain.Location;
import java.util.List;
import java.util.Optional;

public class DefaultRouteProvider extends RouteProvider {

    private final List<Route> possibleRoute;

    public DefaultRouteProvider(List<Route> possibleRoute) {
        this.possibleRoute = possibleRoute;
    }

    @Override
    public Optional<List<Location>> calculateRoute(Location from, Location to) {
        return findValidPath(from, to, possibleRoute);
    }
}
