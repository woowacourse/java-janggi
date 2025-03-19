package janggi.domain;

import janggi.domain.piece.Position;
import java.util.List;

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
}
