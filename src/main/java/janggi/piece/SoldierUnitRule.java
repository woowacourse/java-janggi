package janggi.piece;

import janggi.position.Position;
import janggi.position.Route;
import java.util.ArrayList;
import java.util.List;

public class SoldierUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();

        int x = start.getX();
        int y = start.getY();
        for (Direction direction : Direction.getStraight(Team.CHO)) {
            addRouteIfCanBePosition(direction, x, y, routes);
        }
        return routes;
    }

    private static void addRouteIfCanBePosition(Direction direction, int x, int y, List<Route> routes) {
        int newX = x + direction.getX();
        int newY = y + direction.getY();
        if (Position.isCanBePosition(newX, newY)) {
            routes.add(Route.of(List.of(new Position(newX, newY))));
        }
    }

    @Override
    public UnitType getType() {
        return UnitType.JOL;
    }
}
