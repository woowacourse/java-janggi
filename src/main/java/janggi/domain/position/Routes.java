package janggi.domain.position;

import java.util.Set;
import java.util.stream.Collectors;

public class Routes {
    private final Set<Route> routes;

    public Routes(Set<Route> routes) {
        this.routes = routes;
    }

    public Set<Position> getDestinations() {
        return routes.stream()
                .map(Route::getDestination)
                .collect(Collectors.toSet());
    }
}
