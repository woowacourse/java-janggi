package janggi.domain.space;

import janggi.domain.board.BoardReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

public class Path {
    public static final int NOT_FOUND = -1;
    private static final int SINGLE_STEP_SIZE = 1;
    public static final int START_INDEX = 0;
    public static final int LAST_INDEX_OFFSET = 1;
    private final List<Position> steps;

    private Path(List<Position> steps) {
        this.steps = List.copyOf(steps);
    }

    public static Path ofOneStep(Position current, Direction direction) {
        validatePosition(current, direction);
        Position position = current.move(direction);
        return new Path(List.of(position));
    }

    public static Path ofSequence(Position current, List<Direction> sequence) {
        validatePositions(current, sequence);
        List<Position> positions = new ArrayList<>();
        Position position = current;
        for (Direction direction : sequence) {
            validatePosition(position, direction);
            position = position.move(direction);
            positions.add(position);
        }
        return new Path(positions);
    }

    public static Path ofContinuous(Position current, Direction direction) {
        List<Position> positions = new ArrayList<>();
        Position position = current;
        while (position.canMove(direction)) {
            position = position.move(direction);
            positions.add(position);
        }
        return new Path(positions);
    }

    private static void validatePosition(Position current, Direction direction) {
        if (!current.canMove(direction)) {
            throw new IllegalArgumentException("이동 불가능한 방향으로는 경로를 생성할 수 없습니다.");
        }
    }

    private static void validatePositions(Position current, List<Direction> sequence) {
        if (!current.canMove(sequence)) {
            throw new IllegalArgumentException("유효하지 않은 시퀀스입니다.");
        }
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
