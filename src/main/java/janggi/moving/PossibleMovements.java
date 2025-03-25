package janggi.moving;

import janggi.board.position.Position;
import java.util.List;
import java.util.function.Predicate;

public class PossibleMovements {
    private final List<Movements> possibleMovements;

    public PossibleMovements(List<Movements> sss) {
        this.possibleMovements = sss;
    }

    public Path calculatePath(Position start, Position goal) {
        List<Movements> filteredPossibleMovements = filterMovements(start, goal);
        for (Movements movements : filteredPossibleMovements) {
            Path path = makePath(start, goal, movements);
            if (path == null) {
                continue;
            }
            if (path.lastEquals(goal)) {
                return path;
            }
        }
        throw new IllegalArgumentException("[ERROR] 선택하신 기물은 해당 목적지로 이동할 수 없습니다.");
    }

    private Path makePath(Position start, Position goal, Movements movements) {
        if (movements.isStraight()) {
            return movements.makeStraightPath(start, goal);
        }
        return movements.makePath(start);
    }

    private List<Movements> filterMovements(Position start, Position goal) {
        int columnDifference = goal.subtractColumn(start);
        int rowDifference = goal.subtractRow(start);
        if (columnDifference > 0) {
            return filterRightward(rowDifference);
        }
        if (columnDifference < 0) {
            return filterLeftward(rowDifference);
        }
        if (rowDifference > 0) {
            return getStraightOrFilteredMovements(Movements::isUpward, Movements::isUpStraight);
        }
        return getStraightOrFilteredMovements(Movements::isDownward, Movements::isDownStraight);
    }

    private List<Movements> filterLeftward(int rowDifference) {
        if (rowDifference > 0) {
            return filterMovements(Movements::isUpward, Movements::isLeftward);
        }
        if (rowDifference < 0) {
            return filterMovements(Movements::isDownward, Movements::isLeftward);
        }
        return getStraightOrFilteredMovements(Movements::isLeftward, Movements::isLeftStraight);
    }

    private List<Movements> filterRightward(int rowDifference) {
        if (rowDifference > 0) {
            return filterMovements(Movements::isUpward, Movements::isRightward);
        }
        if (rowDifference < 0) {
            return filterMovements(Movements::isDownward, Movements::isRightward);
        }
        return getStraightOrFilteredMovements(Movements::isRightward, Movements::isRightStraight);
    }

    private List<Movements> getStraightOrFilteredMovements(
            Predicate<Movements> predicate,
            Predicate<Movements> straightPredicate)
    {
        for (Movements movements : possibleMovements) {
            boolean isStraight = straightPredicate.test(movements);
            if (isStraight) {
                return List.of(movements);
            }
        }
        return filterMovements(predicate);
    }

    private List<Movements> filterMovements(Predicate<Movements> predicate) {
        return possibleMovements.stream()
                .filter(predicate)
                .toList();
    }

    private List<Movements> filterMovements(
            Predicate<Movements> firstPredicate,
            Predicate<Movements> secondPredicate) {
        return possibleMovements.stream()
                .filter(firstPredicate)
                .filter(secondPredicate)
                .toList();
    }
}
