package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import java.util.List;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public OneStepStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board, Side side) {
        return generatePaths(current).stream()
                .map(Path::getDestination)
                .filter(dest -> board.isEmpty(dest) || !board.isAlly(dest, side))
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> new Path(List.of(current.move(direction))))
                .toList();
    }
}
