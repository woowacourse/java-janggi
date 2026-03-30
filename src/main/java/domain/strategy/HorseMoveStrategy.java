package domain.strategy;

import domain.HorseMoveRule;
import domain.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy extends MoveStrategy {

    private Map<Position, List<Position>> routesByDestination;

    private HorseMoveStrategy(Position position) {
        super(position);
        this.routesByDestination = createRoutesByDestination();
    }

    public static HorseMoveStrategy of(Position position) {
        return new HorseMoveStrategy(position);
    }

    @Override
    public void updateRoute() {
        this.routesByDestination = createRoutesByDestination();
    }

    private Map<Position, List<Position>> createRoutesByDestination() {
        Map<Position, List<Position>> routesByDestination = new HashMap<>();

        for (HorseMoveRule moveRule : HorseMoveRule.values()) {
            addRoute(routesByDestination, moveRule);
        }

        return routesByDestination;
    }

    private void addRoute(Map<Position, List<Position>> routesByDestination, HorseMoveRule moveRule) {
        routesByDestination.put(
                moveRule.destination(position()),
                moveRule.route(position())
        );
    }

    @Override
    public boolean canMoveTo(Position destination) {
        return routesByDestination.containsKey(destination);
    }

    @Override
    public boolean hasValidPathTo(Position destination, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .noneMatch(routesByDestination.get(destination)::contains);
    }
}
