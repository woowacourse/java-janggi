package janggi.domain;

import janggi.domain.piece.Position;
import java.util.List;
import java.util.Objects;

public class Route {

    private final List<Position> route;
    private final Position destination;

    public Route(final List<Position> route, final Position destination) {
        this.route = route;
        this.destination = destination;
    }

    public boolean hasNotPosition(Position position) {
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
