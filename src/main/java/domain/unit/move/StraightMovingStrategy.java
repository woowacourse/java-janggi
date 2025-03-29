package domain.unit.move;

import domain.movement.Direction;
import domain.movement.Movement;
import domain.position.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class StraightMovingStrategy implements MovingStrategy {

    @Override
    public List<Movement> generatePossibleMovement(Position position) {
        List<Movement> movements = Direction.getStraight().stream()
                .flatMap(direction -> createMovementsInDirection(position, direction).stream())
                .toList();
        List<Movement> possibleMovements = new ArrayList<>(movements);
        possibleMovements.addAll(createPalaceMovement(position));
        return possibleMovements.stream()
                .filter(movement -> movement.canBeRoute(position))
                .toList();
    }

    private List<Movement> createMovementsInDirection(Position position, Direction direction) {
        int maxSteps = position.calculateMaxStep(direction);
        return IntStream.rangeClosed(1, maxSteps)
                .mapToObj(steps -> Movement.of(direction, steps))
                .toList();
    }

    private List<Movement> createPalaceMovement(Position position) {
        List<Movement> movements = new ArrayList<>();
        if (position.equals(Position.of(3, 0)) || position.equals(Position.of(3, 7))) {
            movements.add(Movement.of(Direction.UPPER_RIGHT));
            movements.add(Movement.of(Direction.UPPER_RIGHT, Direction.UPPER_RIGHT));
        }
        if (position.equals(Position.of(5, 0)) || position.equals(Position.of(5, 7))) {
            movements.add(Movement.of(Direction.UPPER_LEFT));
            movements.add(Movement.of(Direction.UPPER_LEFT, Direction.UPPER_LEFT));
        }
        if (position.equals(Position.of(3, 2)) || position.equals(Position.of(3, 9))) {
            movements.add(Movement.of(Direction.LOWER_RIGHT));
            movements.add(Movement.of(Direction.LOWER_RIGHT, Direction.LOWER_RIGHT));
        }
        if (position.equals(Position.of(5, 2)) || position.equals(Position.of(5, 9))) {
            movements.add(Movement.of(Direction.LOWER_LEFT));
            movements.add(Movement.of(Direction.LOWER_LEFT, Direction.LOWER_LEFT));
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
