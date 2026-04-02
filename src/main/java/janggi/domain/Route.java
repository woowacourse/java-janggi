package janggi.domain;

import java.util.List;
import java.util.Objects;

public class Route {

    private final List<Direction> routes;

    public Route(List<Direction> routes) {
        this.routes = routes;
    }

    public List<Direction> getRoutes() {
        return routes;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Route route)) {
            return false;
        }
        return Objects.equals(routes, route.routes);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(routes);
    }
}
