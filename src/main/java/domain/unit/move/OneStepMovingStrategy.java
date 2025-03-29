package domain.unit.move;

import static domain.movement.Direction.LEFT;
import static domain.movement.Direction.LOWER;
import static domain.movement.Direction.RIGHT;
import static domain.movement.Direction.UPPER;

import domain.movement.Direction;
import domain.movement.Movement;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;

public class OneStepMovingStrategy implements MovingStrategy {

    @Override
    public List<Movement> generatePossibleMovement(Position position) {
        List<Movement> possibleMovements = new ArrayList<>(getMovements());
        possibleMovements.addAll(createPalaceMovement(position));
        return possibleMovements.stream()
                .filter(movement -> movement.canBeRoute(position))
                .toList();
    }

    private static List<Movement> getMovements() {
        return List.of(
                Movement.of(UPPER),
                Movement.of(LOWER),
                Movement.of(LEFT),
                Movement.of(RIGHT));
    }

    private List<Movement> createPalaceMovement(Position position) {
        List<Movement> movements = new ArrayList<>();
        if (position.equals(Position.of(3, 0)) || position.equals(Position.of(3, 7))) {
            movements.add(Movement.of(Direction.UPPER_RIGHT));
        }
        if (position.equals(Position.of(5, 0)) || position.equals(Position.of(5, 7))) {
            movements.add(Movement.of(Direction.UPPER_LEFT));
        }
        if (position.equals(Position.of(3, 2)) || position.equals(Position.of(3, 9))) {
            movements.add(Movement.of(Direction.LOWER_RIGHT));
        }
        if (position.equals(Position.of(5, 2)) || position.equals(Position.of(5, 9))) {
            movements.add(Movement.of(Direction.LOWER_LEFT));
        }
        if (position.equals(Position.of(4, 1)) || position.equals(Position.of(4, 8))) {
            movements.add(Movement.of(Direction.LOWER_LEFT));
            movements.add(Movement.of(Direction.LOWER_RIGHT));
            movements.add(Movement.of(Direction.UPPER_LEFT));
            movements.add(Movement.of(Direction.UPPER_RIGHT));
        }
        return movements;
    }
}
