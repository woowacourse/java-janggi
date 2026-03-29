package domain.strategy;

import domain.ElephantMoveRule;
import domain.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy extends MoveStrategy {

    private Map<Position, List<Position>> routesByDestination;

    private ElephantMoveStrategy(Position position) {
        super(position);
        this.routesByDestination = createRoutesByDestination();
    }

    public static ElephantMoveStrategy of(Position position) {
        return new ElephantMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
        this.routesByDestination = createRoutesByDestination();
    }

    private Map<Position, List<Position>> createRoutesByDestination() {
        Map<Position, List<Position>> routesByDestination = new HashMap<>();

        for (ElephantMoveRule moveRule : ElephantMoveRule.values()) {
            addRoute(routesByDestination, moveRule);
        }

        return routesByDestination;
    }

    private void addRoute(Map<Position, List<Position>> routesByDestination, ElephantMoveRule moveRule) {
        routesByDestination.put(moveRule.destination(position()), moveRule.route(position()));
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return routesByDestination.containsKey(destination);
    }

    @Override
    public boolean hasPieceInPath(Position destination, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .anyMatch(routesByDestination.get(destination)::contains);
    }
}
