package domain.unit.rule;

import static domain.unit.Direction.LEFT;
import static domain.unit.Direction.LOWER;
import static domain.unit.Direction.LOWER_LEFT;
import static domain.unit.Direction.LOWER_RIGHT;
import static domain.unit.Direction.RIGHT;
import static domain.unit.Direction.UPPER;
import static domain.unit.Direction.UPPER_LEFT;
import static domain.unit.Direction.UPPER_RIGHT;

import domain.position.Position;
import domain.position.Route;
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.ArrayList;
import java.util.List;

public class HorseUnitRule implements UnitRule {

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
                Movement.of(UPPER, UPPER_RIGHT),
                Movement.of(UPPER, UPPER_LEFT),

                Movement.of(LOWER, LOWER_RIGHT),
                Movement.of(LOWER, LOWER_LEFT),

                Movement.of(LEFT, UPPER_LEFT),
                Movement.of(LEFT, LOWER_LEFT),

                Movement.of(RIGHT, UPPER_RIGHT),
                Movement.of(RIGHT, LOWER_RIGHT)
        );
    }

    @Override
    public UnitType getType() {
        return UnitType.HORSE;
    }
}
