package domain.movement;

import domain.board.Position;
import java.util.List;
import java.util.stream.IntStream;

public final class Directions {
    private final List<Direction> directions;

    public Directions(List<Direction> directions) {
        this.directions = List.copyOf(directions);
    }

    public Delta calculateFinalDestinationDelta() {
        return directions.stream()
                .map(Direction::delta)
                .reduce(Delta.ZERO, Delta::add);
    }

    public List<Position> apply(Position sourcePosition) {
        return IntStream.rangeClosed(1, directions.size())
                .mapToObj(this::calculateDeltaTo)
                .map(sourcePosition::shift)
                .toList();
    }

    private Delta calculateDeltaTo(int step) {
        return directions.stream()
                .limit(step)
                .map(Direction::delta)
                .reduce(Delta.ZERO, Delta::add);
    }
}
