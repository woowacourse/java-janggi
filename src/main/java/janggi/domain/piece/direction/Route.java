package janggi.domain.piece.direction;

import janggi.domain.piece.Piece;
import java.util.List;
import java.util.Objects;

public class Route {

    private final List<Position> route;

    public Route(final List<Position> route) {
        this.route = route;
    }

    public boolean hasPosition(final Piece piece) {
        return route.stream()
                .anyMatch(piece::isSamePosition);
    }

    public boolean isDestination(final Piece piece) {
        return piece.isSamePosition(route.getLast());
    }

    public boolean isDestination(final Position position) {
        return position.equals(route.getLast());
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
