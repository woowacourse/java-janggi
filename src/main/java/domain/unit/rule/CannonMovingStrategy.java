package domain.unit.rule;

import domain.position.Position;
import domain.unit.Direction;
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class CannonMovingStrategy implements MovingStrategy {

    @Override
    public List<Movement> generatePossibleMovement(Position position) {
        List<Movement> movements = Direction.getStraight().stream()
                .flatMap(direction -> createMovementsInDirection(direction).stream())
                .toList();
        List<Movement> possibleMovements = new ArrayList<>(movements);
        possibleMovements.addAll(createPalaceMovement(position));
        return possibleMovements.stream()
                .filter(movement -> movement.canBeRoute(position))
                .toList();
    }


    private List<Movement> createMovementsInDirection(Direction direction) {
        int maxSteps = calculateMaxSteps(direction);
        return IntStream.rangeClosed(1, maxSteps)
                .mapToObj(steps -> createMovement(direction, steps))
                .toList();
    }

    private Movement createMovement(Direction direction, int steps) {
        Direction[] directions = new Direction[steps];
        Arrays.fill(directions, direction);
        return Movement.of(directions);
    }

    private int calculateMaxSteps(Direction direction) {
        if (direction == Direction.UPPER || direction == Direction.LOWER) {
            return Position.Y_MAX;
        }
        return Position.X_MAX;
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

    @Override
    public UnitType getType() {
        return UnitType.CANNON;
    }
}
