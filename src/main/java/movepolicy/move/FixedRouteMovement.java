package movepolicy.move;

import java.util.List;
import java.util.Optional;
import pieces.Side;
import position.Position;

public class FixedRouteMovement implements Movement {

    private final List<Route> routes;

    public FixedRouteMovement(final List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean canReach(final Position departure, final Position destination, final Side side) {
        return routeOf(departure, destination, side).isPresent();
    }

    @Override
    public List<Position> findPathPositions(final Position departure, final Position destination, final Side side) {
        final Route route = routeOf(departure, destination, side)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이동입니다."));
        return route.findPathPositions(departure, side);
    }

    private Optional<Route> routeOf(final Position departure, final Position destination, final Side side) {
        return routes.stream()
            .filter(route -> canReachDestination(departure, destination, side, route))
            .findFirst();
    }

    private boolean canReachDestination(
        final Position departure, Position destination, Side side, Route route) {
        final Optional<Position> position = route.destinationOf(departure, side);
        return position
            .filter(destination::equals)
            .isPresent();
    }
}
