package domain.unit;

import domain.Route;
import java.util.ArrayList;
import java.util.List;

public class ElephantUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Point start) {
        List<Route> routes = new ArrayList<>();

        List<Integer> dx = List.of(0, 1, 0, -1);
        List<Integer> dy = List.of(1, 0, -1, 0);

        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < dx.size(); i++) {
            Point pivot = new Point(x + dx.get(i), y + dy.get(i));
            if (i == 0) {
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() + 1, pivot.getY() + 1),
                        new Point(pivot.getX() + 2, pivot.getY() + 2))));
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() - 1, pivot.getY() + 1),
                        new Point(pivot.getX() - 2, pivot.getY() + 2))));
            }
            if (i == 1) {
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() + 1, pivot.getY() + 1),
                        new Point(pivot.getX() + 2, pivot.getY() + 2))));
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() + 1, pivot.getY() - 1),
                        new Point(pivot.getX() + 2, pivot.getY() - 2))));
            }
            if (i == 2) {
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() + 1, pivot.getY() - 1),
                        new Point(pivot.getX() + 2, pivot.getY() - 2))));
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() - 1, pivot.getY() - 1),
                        new Point(pivot.getX() - 2, pivot.getY() - 2))));
            }
            if (i == 3) {
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() - 1, pivot.getY() + 1),
                        new Point(pivot.getX() - 2, pivot.getY() + 2))));
                routes.add(Route.of(List.of(pivot, new Point(pivot.getX() - 1, pivot.getY() - 1),
                        new Point(pivot.getX() - 2, pivot.getY() - 2))));
            }
        }
        return routes;
    }

    @Override
    public String getType() {
        return "상";
    }
}
