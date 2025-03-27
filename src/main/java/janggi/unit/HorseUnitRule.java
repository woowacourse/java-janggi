package janggi.unit;

import janggi.position.Point;
import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class HorseUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        dfs(0, Direction.NONE, new ArrayList<>(), Point.of(start.getX(), start.getY()), routes);
        return routes;
    }

    private void dfs(int depth, Direction before, List<Point> route, Point prevPoint, List<Route> routes) {
        if (depth == 2) {
            if (route.stream().allMatch(point -> Position.isCanBePosition(point.getX(), point.getY()))) {
                routes.add(Route.of(route.stream().map(Position::from).toList()));
            }
            return;
        }
        for (Direction direction : before.getNextWithDiagonal()) {
            Point next = Point.of(prevPoint.getX() + direction.getX(), prevPoint.getY() + direction.getY());
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
