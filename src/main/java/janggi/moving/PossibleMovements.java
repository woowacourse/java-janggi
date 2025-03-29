package janggi.moving;

import janggi.board.position.Position;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class PossibleMovements {
    private final List<Movements> possibleMovements;

    public PossibleMovements(List<Movements> possibleMovements) {
        this.possibleMovements = possibleMovements;
    }

    public Path calculatePath(Position start, Position goal) {
        return filterMovements(start, goal).stream()
                .map(movements -> makePath(start, goal, movements))
                .filter(Path::isValidPath)
                .filter(path -> path.lastEquals(goal))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다."));
    }

    private List<Movements> filterMovements(Position start, Position goal) {
        if (goal.isColumnDifferencePositive(start)) {
            return filterMovementsByDirection(start, goal, Movements::isRightward, Movements::isRightStraight);
        }
        if (goal.isColumnDifferenceNegative(start)) {
            return filterMovementsByDirection(start, goal, Movements::isLeftward, Movements::isLeftStraight);
        }
        if (goal.isRowDifferencePositive(start)) {
            return filterStraightOrGeneralMovements(Movements::isUpward, Movements::isUpStraight);
        }
        return filterStraightOrGeneralMovements(Movements::isDownward, Movements::isDownStraight);
    }

    private List<Movements> filterMovementsByDirection(
            Position start,
            Position goal,
            Predicate<Movements> horizontalCondition,
            Predicate<Movements> horizontalStraightCondition
    ) {
        if (goal.isRowDifferencePositive(start)) {
            return filterGeneralMovements(Movements::isUpward, horizontalCondition);
        }
        if (goal.isRowDifferenceNegative(start)) {
            return filterGeneralMovements(Movements::isDownward, horizontalCondition);
        }
        return filterStraightOrGeneralMovements(horizontalCondition, horizontalStraightCondition);
    }

    private List<Movements> filterStraightOrGeneralMovements(
            Predicate<Movements> predicate,
            Predicate<Movements> straightPredicate
    ) {
        return possibleMovements.stream()
                .filter(straightPredicate::test)
                .findAny()
                .map(List::of)
                .orElseGet(() -> filterGeneralMovements(predicate));
    }

    private List<Movements> filterGeneralMovements(Predicate<Movements>... conditions) {
        return possibleMovements.stream()
                .filter(Stream.of(conditions)
                        .reduce(movements -> true, Predicate::and))
                .toList();
    }

    private Path makePath(Position start, Position goal, Movements movements) {
        if (movements.isStraight()) {
            return movements.makeStraightPath(start, goal);
        }
        return movements.makePath(start);
    }
}
