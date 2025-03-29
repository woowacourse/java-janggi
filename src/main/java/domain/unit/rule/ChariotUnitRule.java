package domain.unit.rule;

import domain.position.Position;
import domain.unit.Direction;
import domain.unit.Movement;
import domain.unit.UnitType;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class ChariotUnitRule implements UnitRule {

    @Override
    public List<Movement> generatePossibleMovement() {
        return Direction.getStraight().stream()
                .flatMap(direction -> createMovementsInDirection(direction).stream())
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

    public UnitType getType() {
        return UnitType.CHARIOT;
    }
}
