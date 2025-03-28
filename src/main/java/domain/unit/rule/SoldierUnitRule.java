package domain.unit.rule;

import static domain.unit.Direction.LEFT;
import static domain.unit.Direction.LOWER;
import static domain.unit.Direction.RIGHT;
import static domain.unit.Direction.UPPER;

import domain.position.Position;
import domain.position.Route;
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.ArrayList;
import java.util.List;

public class SoldierUnitRule implements UnitRule {

    public List<Route> calculateAllRoute(Position start) {
        List<Route> routes = new ArrayList<>();
        List<Movement> movements = generatePossibleMovement();
        for (Movement movement : movements) {
            try {
                Route route = movement.calculateRouteBy(start);
                routes.add(route);
            } catch (IllegalArgumentException ignored) {
            }
        }
        return routes;
    }

    @Override
    public List<Movement> generatePossibleMovement() {
        return List.of(
                Movement.of(UPPER),
                Movement.of(LOWER),
                Movement.of(LEFT),
                Movement.of(RIGHT));
    }

    @Override
    public UnitType getType() {
        return UnitType.SOLDIER;
    }
}
