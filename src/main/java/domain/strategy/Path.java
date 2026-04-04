package domain.strategy;

import domain.Position;
import domain.board.BoardReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Path {
    public static final int NOT_FOUND = -1;
    private static final int SINGLE_STEP_SIZE = 1;
    public static final int START_INDEX = 0;
    public static final int LAST_INDEX_OFFSET = 1;
    private final List<Position> steps;

    public Path(List<Position> steps) {
        this.steps = List.copyOf(steps);
    }

    public Path takeWhile(Predicate<Position> condition) {
        List<Position> result = steps.stream()
                .takeWhile(condition)
                .toList();
        return new Path(result);
    }

    public Optional<Position> findFirst(Predicate<Position> condition) {
        return steps.stream()
                .filter(condition)
                .findFirst();
    }

    public Path after(Position target) {
        int index = steps.indexOf(target);
        if (index == NOT_FOUND || index == getLastIndex()) {
            return new Path(List.of());
        }
        return new Path(steps.subList(index + LAST_INDEX_OFFSET, steps.size()));
    }

    public boolean isBlocked(BoardReader board) {
        if (steps.size() <= SINGLE_STEP_SIZE) {
            return false;
        }
        return steps.subList(START_INDEX, getLastIndex()).stream()
                .anyMatch(pos -> !board.isEmpty(pos));
    }

    public Position getDestination() {
        return steps.getLast();
    }

    public List<Position> toList() {
        return steps;
    }

    private int getLastIndex() {
        return steps.size() - LAST_INDEX_OFFSET;
    }
}
