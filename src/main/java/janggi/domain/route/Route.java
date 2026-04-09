package janggi.domain.route;

import janggi.domain.common.Direction;
import janggi.domain.common.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Route {

    private final List<Direction> routes;

    public Route(List<Direction> routes) {
        this.routes = routes;
    }

    public List<Position> applyDirections(Position position) {
        List<Position> routePositions = new ArrayList<>();
        Position startPosition = position;
        for (Direction direction : routes) {
            Optional<Position> nextPosition = direction.nextPosition(startPosition);
            if (nextPosition.isEmpty()) {
                return new ArrayList<>();
            }
            startPosition = nextPosition.get();
            routePositions.add(startPosition);
        }
        return routePositions;
    }

    public void applyContinuousDirections(Position position, Map<Position, List<Position>> continuousRoutes) {
        Position startPosition = position;
        for (Direction direction : routes) {
            direction.nextContinuousPosition(startPosition, continuousRoutes);
        }
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
