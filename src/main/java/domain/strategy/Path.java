package domain.strategy;

import domain.Position;
import domain.board.BoardReader;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Path {
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
        if (index == -1 || index == steps.size() - 1) {
            return new Path(List.of());
        }
        return new Path(steps.subList(index + 1, steps.size()));
    }

    public boolean isBlocked(BoardReader board) {
        if (steps.size() <= 1) {
            return false;
        }
        return steps.subList(0, steps.size() - 1).stream()
                .anyMatch(pos -> !board.isEmpty(pos));
    }

    public Position getDestination() {
        return steps.getLast();
    }

    public List<Position> toList() {
        return steps;
    }
}
