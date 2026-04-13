package domain.strategy;

import domain.HorseMoveRule;
import domain.Position;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HorseMoveStrategy extends MoveStrategy {

    private static final HorseMoveStrategy INSTANCE = new HorseMoveStrategy();

    private HorseMoveStrategy() {
    }

    public static HorseMoveStrategy getInstance() {
        return INSTANCE;
    }

    private Map<Position, List<Position>> createRoutesByDestination(Position currentPosition) {
        Map<Position, List<Position>> routesByDestination = new HashMap<>();

        for (HorseMoveRule moveRule : HorseMoveRule.values()) {
            addRoute(currentPosition, routesByDestination, moveRule);
        }

        return routesByDestination;
    }

    private void addRoute(Position currentPosition, Map<Position, List<Position>> routesByDestination,
                          HorseMoveRule moveRule) {
        routesByDestination.put(
                moveRule.destination(currentPosition),
                moveRule.route(currentPosition)
        );
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
