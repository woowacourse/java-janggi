package domain.unit;

import domain.position.Position;
import domain.position.Route;
import domain.UnitType;
import java.util.ArrayList;
import java.util.List;

public class JolUnitRule implements UnitRule {
    @Override
    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();

        List<Integer> dx = List.of(0, 1, 0, -1);
        List<Integer> dy = List.of(1, 0, -1, 0);

        int x = start.getX();
        int y = start.getY();
        for (int i = 0; i < dx.size(); i++) {
            try {
                routes.add(Route.of(
                        List.of(new Position(x + dx.get(i), y + dy.get(i))))
                );
            } catch (IllegalArgumentException exception) {
                continue;
            }
        }
        return routes;
    }

    @Override
    public UnitType getType() {
        return UnitType.JOL;
    }
}
