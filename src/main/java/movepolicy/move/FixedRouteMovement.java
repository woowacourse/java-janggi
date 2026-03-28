package movepolicy.move;

import java.util.List;
import pieces.Side;
import position.Position;

public class FixedRouteMovement implements Movement {

    private final List<Route> routes;

    public FixedRouteMovement(List<Route> routes) {
        this.routes = routes;
    }

    @Override
    public boolean canReach(Position departure, Position destination, Side side) {
        return routes.stream()
            .anyMatch(route -> route.destinationOf(departure, side).equals(destination));
    }

    @Override
    public List<Position> getPathPositions(Position departure, Position destination, Side side) {
        return routes.stream()
            .filter(route -> route.destinationOf(departure, side).equals(destination))
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 이동입니다"))
            .getPathPositionsOf(departure, side);
    }
}
