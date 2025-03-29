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
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.List;

public class GeneralMovingStrategy implements MovingStrategy {

    @Override
    public List<Movement> generatePossibleMovement(Position position) {
        return getMovements().stream()
                .filter(movement -> movement.canBeRoute(position))
                .filter(movement -> movement.calculateRouteBy(position).searchDestination(position).isPalace())
                .toList();
    }

    private static List<Movement> getMovements() {
        return List.of(
                Movement.of(UPPER),
                Movement.of(UPPER_RIGHT),
                Movement.of(RIGHT),
                Movement.of(LOWER_RIGHT),
                Movement.of(LOWER),
                Movement.of(LOWER_LEFT),
                Movement.of(LEFT),
                Movement.of(UPPER_LEFT)
        );
    }

    @Override
    public UnitType getType() {
        return UnitType.GENERAL;
    }
}
