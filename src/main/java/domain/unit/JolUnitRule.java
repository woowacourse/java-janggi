package domain.unit;

import domain.Route;
import java.util.ArrayList;
import java.util.List;

public class JolUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Point start) {
        List<Route> routes = new ArrayList<>();

        List<Integer> dx = List.of(0, 1, 0, -1);
        List<Integer> dy = List.of(1, 0, -1, 0);

        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < dx.size(); i++) {
            routes.add(Route.of(
                    List.of(new Point(x + dx.get(i), y + dy.get(i))))
            );
        }
        return routes;
    }

    @Override
    public String getType() {
        return "졸";
    }
}
