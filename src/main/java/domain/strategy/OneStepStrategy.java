package domain.strategy;

import domain.Position;
import domain.Side;
import domain.board.BoardReader;
import java.util.List;
import java.util.stream.Collectors;

public class OneStepStrategy implements MovementStrategy {
    private final List<Direction> directions;

    public OneStepStrategy(List<Direction> directions) {
        this.directions = directions;
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
        return directions.stream()
                .filter(current::canMove)
                .map(direction -> new Path(List.of(current.move(direction))))
                .toList();
    }
}
