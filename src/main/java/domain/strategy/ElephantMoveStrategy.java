package domain.strategy;

import domain.ElephantMoveRule;
import domain.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElephantMoveStrategy extends MoveStrategy {

    private static final ElephantMoveStrategy INSTANCE = new ElephantMoveStrategy();

    private ElephantMoveStrategy() {
    }

    public static ElephantMoveStrategy getInstance() {
        return INSTANCE;
    }

    private Map<Position, List<Position>> createRoutesByDestination(Position currentPosition) {
        Map<Position, List<Position>> routesByDestination = new HashMap<>();

        for (ElephantMoveRule moveRule : ElephantMoveRule.values()) {
            addRoute(currentPosition, routesByDestination, moveRule);
        }

        return routesByDestination;
    }

    private void addRoute(Position currentPosition, Map<Position, List<Position>> routesByDestination,
                          ElephantMoveRule moveRule) {
        routesByDestination.put(moveRule.destination(currentPosition), moveRule.route(currentPosition));
    }

    @Override
    public boolean canMoveTo(Position currentPosition, Position destination) {
        return createRoutesByDestination(currentPosition).containsKey(destination);
    }

    @Override
    public boolean hasValidPathTo(Position currentPosition, Position destination, List<Position> occupiedPositions) {
        return occupiedPositions.stream()
                .noneMatch(createRoutesByDestination(currentPosition).get(destination)::contains);
    }
}
