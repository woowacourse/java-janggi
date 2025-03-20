package domain.unit;

import domain.Position;
import domain.Route;
import domain.UnitType;
import java.util.ArrayList;
import java.util.List;

public class ElephantUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();

        List<Integer> dx = List.of(0, 1, 0, -1);
        List<Integer> dy = List.of(1, 0, -1, 0);

        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < dx.size(); i++) {
            try {
                Position pivot = new Position(x + dx.get(i), y + dy.get(i));
                if (i == 0) {
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() + 1, pivot.getY() + 1),
                            new Position(pivot.getX() + 2, pivot.getY() + 2))));
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() - 1, pivot.getY() + 1),
                            new Position(pivot.getX() - 2, pivot.getY() + 2))));
                }
                if (i == 1) {
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() + 1, pivot.getY() + 1),
                            new Position(pivot.getX() + 2, pivot.getY() + 2))));
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() + 1, pivot.getY() - 1),
                            new Position(pivot.getX() + 2, pivot.getY() - 2))));
                }
                if (i == 2) {
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() + 1, pivot.getY() - 1),
                            new Position(pivot.getX() + 2, pivot.getY() - 2))));
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() - 1, pivot.getY() - 1),
                            new Position(pivot.getX() - 2, pivot.getY() - 2))));
                }
                if (i == 3) {
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() - 1, pivot.getY() + 1),
                            new Position(pivot.getX() - 2, pivot.getY() + 2))));
                    routes.add(Route.of(List.of(pivot, new Position(pivot.getX() - 1, pivot.getY() - 1),
                            new Position(pivot.getX() - 2, pivot.getY() - 2))));
                }
            } catch (IllegalArgumentException exception) {
                continue;
            }
        }
        return routes;
    }

    @Override
    public UnitType getType() {
        return UnitType.ELEPHANT;
    }
}
