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

    public static List<Route> createRoutes(final List<Position> positions) {
        List<Route> routes = new ArrayList<>();
        for (Position position : positions) {
            routes.add(new Route(position));
        }
        return routes;
    }

    public void addRoute(final Position... position) {
        positions.addAll(List.of(position));
    }

    public Position getDestination() {
        return positions.getLast();
    }

    public List<Position> getIntermediatePositions() {
        return positions.subList(0, positions.size() - 1);
    }

    public List<Position> getPositions() {
        return Collections.unmodifiableList(positions);
    }

}
