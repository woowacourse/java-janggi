package janggi.unit;

import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class HorseUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        dfs(0, Direction.NONE, new ArrayList<>(), start, routes);
        return routes;
    }

    private void dfs(int depth, Direction before, List<Position> route, Position prevPoint, List<Route> routes) {
        if (depth == 2) {
            if (route.stream().allMatch(position -> Position.isCanBePosition(position.getX(), position.getY()))) {
                routes.add(Route.of(route));
            }
            return;
        }
        for (Direction direction : before.getNextWithDiagonal()) {
            Position next = new Position(prevPoint.getX() + direction.getX(), prevPoint.getY() + direction.getY());
            route.add(next);
            dfs(depth + 1, direction, route, next, routes);
            route.remove(next);
        }
    }

    @Override
    public UnitType getType() {
        return UnitType.HORSE;
    }
}
