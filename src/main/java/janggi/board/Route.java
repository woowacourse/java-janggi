package janggi.board;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Route {

    private final List<Position> positions;

    public Route() {
        this.positions = new ArrayList<>();
    }

    public Route(final Position position) {
        this.positions = new ArrayList<>();
        addRoute(position);
    }

    public void addRoute(final Position position) {
        positions.add(position);
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }

}
