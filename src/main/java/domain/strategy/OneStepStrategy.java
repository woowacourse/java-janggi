package domain.strategy;

import domain.Position;
import domain.board.BoardReader;
import java.util.List;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public OneStepStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current).stream()
                .map(Path::getDestination)
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> Path.ofOneStep(current, direction))
                .toList();
    }
}
