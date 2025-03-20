package janggi.domain;

import janggi.domain.piece.Piece;
import janggi.domain.piece.Position;
import java.util.List;
import java.util.Objects;

public class Route {

    private final List<Position> route;

    public Route(final List<Position> route) {
        this.route = route;
    }

    public boolean hasPosition(final Piece piece) {
        for (final Position position : route) {
            if (piece.isSamePosition(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDestination(final Piece piece) {
        return piece.isSamePosition(route.getLast());
    }

    public Position getDestination() {
        return route.getLast();
    }

    @Override
    public boolean equals(final Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Route route1 = (Route) o;
        return Objects.equals(route, route1.route);
    }

    @Override
    public int hashCode() {
        return Objects.hash(route);
    }
}
