package domain.unit.move;

import static domain.movement.Direction.LEFT;
import static domain.movement.Direction.LOWER;
import static domain.movement.Direction.LOWER_LEFT;
import static domain.movement.Direction.LOWER_RIGHT;
import static domain.movement.Direction.RIGHT;
import static domain.movement.Direction.UPPER;
import static domain.movement.Direction.UPPER_LEFT;
import static domain.movement.Direction.UPPER_RIGHT;

import domain.movement.Movement;
import domain.position.Position;
import java.util.List;

public class ElephantMovingStrategy implements MovingStrategy {

    @Override
    public List<Movement> generatePossibleMovement(Position position) {
        return getMovements().stream()
                .filter(movement -> movement.canBeRoute(position))
                .toList();
    }

    private static List<Movement> getMovements() {
        return List.of(
                Movement.of(UPPER, UPPER_RIGHT, UPPER_RIGHT),
                Movement.of(UPPER, UPPER_LEFT, UPPER_LEFT),

                Movement.of(LOWER, LOWER_RIGHT, LOWER_RIGHT),
                Movement.of(LOWER, LOWER_LEFT, LOWER_LEFT),

                Movement.of(LEFT, UPPER_LEFT, UPPER_LEFT),
                Movement.of(LEFT, LOWER_LEFT, LOWER_LEFT),

                Movement.of(RIGHT, UPPER_RIGHT, UPPER_RIGHT),
                Movement.of(RIGHT, LOWER_RIGHT, LOWER_RIGHT)
        );
    }
}
