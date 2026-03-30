package movepolicy.move;

import java.util.List;
import java.util.Optional;
import pieces.Side;
import position.Position;

public class FixedRouteMovement implements Movement {

    private final List<Route> routes;

    public FixedRouteMovement(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        return routeOf(departure, destination, side).isPresent();
    }

    @Override
    public List<Position> findPathPositions(Position departure, Position destination, Side side) {
        return routeOf(departure, destination, side)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이동입니다."))
            .findPathPositions(departure, side);
    }

    private Optional<Route> routeOf(Position departure, Position destination, Side side) {
        return routes.stream()
            .filter(route -> isPresent(departure, destination, side, route))
            .findFirst();
    }

    private boolean isPresent(Position departure, Position destination, Side side, Route route) {
        return route.destinationOf(departure, side)
            .filter(destination::equals)
            .isPresent();
    }
}
