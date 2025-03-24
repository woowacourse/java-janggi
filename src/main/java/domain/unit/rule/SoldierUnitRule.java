package domain.unit.rule;

import domain.position.Point;
import domain.position.Position;
import domain.position.Route;
import domain.unit.UnitType;
import java.util.ArrayList;
import java.util.List;

public class SoldierUnitRule implements UnitRule {

    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();

        List<Integer> dx = List.of(0, 1, 0, -1);
        List<Integer> dy = List.of(1, 0, -1, 0);

        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < dx.size(); i++) {
            List<Point> route = List.of(Point.of(x + dx.get(i), y + dy.get(i)));
            if (route.stream().allMatch(Position::isCanBePosition)) {
                routes.add(Route.of(route.stream().map(Position::from).toList()));
            }
        }
        return routes;
    }

    @Override
    public UnitType getType() {
        return UnitType.SOLDIER;
    }
}
