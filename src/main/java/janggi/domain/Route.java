package janggi.domain;

import janggi.domain.piece.Position;
import java.util.Objects;
import java.util.Set;

public class Route {

    private final Set<Position> route;
    private final Position destination;

    public Route(final Set<Position> route, final Position destination) {
        this.route = route;
        this.destination = destination;
    }

    public boolean hasNotPosition(final Position position) {
        return !route.contains(position);
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Route route1 = (Route) o;
        return Objects.equals(route, route1.route) && Objects.equals(destination, route1.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route, destination);
    }
}
