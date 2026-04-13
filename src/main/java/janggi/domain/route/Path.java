package janggi.domain.route;

import janggi.domain.board.Direction;
import janggi.domain.board.Position;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class Path implements Iterable<Position> {

    private final List<Position> positions;

    public Path() {
        this.positions = new ArrayList<>();
    }

    public void add(Position nextPosition) {
        positions.add(nextPosition);
    }

    public boolean isEmpty() {
        return positions.isEmpty();
    }

    @Override
    public Iterator<Position> iterator() {
        return positions.iterator();
    }

    @Override
    public String toString() {
        return positions.toString();
    }

    public static Optional<Path> fromSequence(Position start, List<Direction> sequence) {
        Path path = new Path();
        Position current = start;

        for (Direction direction : sequence) {
            Optional<Position> next = current.tryMove(direction);
            if (next.isEmpty()) {
                return Optional.empty();
            }
            current = next.get();
            path.add(current);
        }
        return Optional.of(path);
    }

    public static Path fromContinuousMove(Position start, Direction direction) {
        Path path = new Path();
        Optional<Position> nextCandidate = start.tryMove(direction);

        while (nextCandidate.isPresent()) {
            Position current = nextCandidate.get();
            path.add(current);
            nextCandidate = current.tryMove(direction);
        }

        return path;
    }

    public static Path fromPalaceContinuousMove(Position start, Direction direction) {
        Path path = new Path();
        Optional<Position> nextCandidate = start.tryMove(direction);

        // 다음 칸이 궁성 안일 때까지만 반복
        while (nextCandidate.isPresent() && nextCandidate.get().isPalace()) {
            Position current = nextCandidate.get();
            path.add(current);
            nextCandidate = current.tryMove(direction);
        }

        return path;
    }
}
