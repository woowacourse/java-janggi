package janggi.domain.move;

import janggi.domain.board.BoardReader;
import janggi.domain.space.Direction;
import janggi.domain.space.Path;
import janggi.domain.space.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class SlideStrategy implements MovementStrategy {
    private final List<Direction> defaultDirections;

    public SlideStrategy(List<Direction> defaultDirections) {
        this.defaultDirections = defaultDirections;
    }

    @Override
    public List<Position> getMovablePositions(Position current, BoardReader board) {
        return generatePaths(current, board).stream()
                .flatMap(path -> getReachablePositions(path, board).stream())
                .toList();
    }

    @Override
    public List<Path> generatePaths(Position current, BoardReader board) {
        Stream<Path> defaultPaths = defaultDirections.stream()
                .filter(current::canMove)
                .map(direction -> Path.ofContinuous(current, direction));

        Stream<Path> diagonalPaths = board.getPalaceDiagonals(current).stream()
                .filter(current::canMove)
                .map(direction -> Path.ofContinuous(current, direction)
                        .takeWhile(board::isInsidePalace));

        return Stream.concat(defaultPaths, diagonalPaths).toList();
    }

    private List<Position> getReachablePositions(Path path, BoardReader board) {
        List<Position> reachable = new ArrayList<>(path.takeWhile(board::isEmpty).toList());
        Optional<Position> obstacle = path.findFirst(pos -> !board.isEmpty(pos));
        obstacle.ifPresent(reachable::add);
        return reachable;
    }
}
