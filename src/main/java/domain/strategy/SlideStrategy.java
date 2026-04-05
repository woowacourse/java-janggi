package domain.strategy;

import domain.Position;
import domain.board.BoardReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SlideStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public SlideStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current).stream()
                .flatMap(path -> getReachablePositions(path, board).stream())
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> Path.ofContinuous(current, direction))
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board) {
        List<Position> reachable = new ArrayList<>(path.takeWhile(board::isEmpty).toList());
        Optional<Position> obstacle = path.findFirst(pos -> !board.isEmpty(pos));
        obstacle.ifPresent(reachable::add);
        return reachable;
    }
}
