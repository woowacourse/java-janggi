package janggi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Route {

    private final List<Direction> routes;

    public Route(List<Direction> routes) {
        this.routes = new ArrayList<>(routes);
    }

    public List<Position> convertToPositions(Position currentPosition) {
        List<Position> positions = new ArrayList<>();
        for (Direction direction : routes) {
            if (currentPosition.cannotMoveTo(direction)) {
                return Collections.emptyList();
            }
            currentPosition = direction.sumDirection(currentPosition);
            positions.add(currentPosition);
        }
        return positions;
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
