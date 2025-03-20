package domain.unit;

import domain.Direction;
import domain.position.Point;
import domain.position.Position;
import domain.position.Route;
import domain.UnitType;
import java.util.ArrayList;
import java.util.List;

public class ElephantUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        dfs(0, Direction.NONE, new ArrayList<>(), Point.of(start.getX(), start.getY()), routes);
        return routes;
    }

    public void dfs(int depth, Direction before, List<Point> route, Point prevPoint, List<Route> routes) {
        if (depth == 3) {
            if (route.stream().allMatch(Position::isCanBePosition)) {
                routes.add(Route.of(route.stream().map(Position::from).toList()));
            }
            return;
        }
        for (Direction direction : before.getNext()) {
            Point next = Point.of(prevPoint.getX() + direction.getX(), prevPoint.getY() + direction.getY());
            route.add(next);
            dfs(depth + 1, direction, route, next, routes);
            route.remove(next);
        }
    }

    @Override
    public UnitType getType() {
        return UnitType.ELEPHANT;
    }
}
