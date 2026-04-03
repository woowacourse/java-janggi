package domain.strategy;

import domain.Position;
import domain.Side;
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
    public List<Position> getMovablePositions(Position current, BoardReader board, Side side) {
        return generatePaths(current).stream()
                .flatMap(path -> getReachablePositions(path, board, side).stream())
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current) {
        return defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> createPath(current, direction))
                .toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board, Side side) {
        List<Position> reachable = new ArrayList<>(path.takeWhile(board::isEmpty).toList());
        Optional<Position> obstacle = path.findFirst(pos -> !board.isEmpty(pos));

        obstacle.filter(pos -> !board.getPiece(pos).isAlly(side))
                .ifPresent(reachable::add);
        return reachable;
    }
}
