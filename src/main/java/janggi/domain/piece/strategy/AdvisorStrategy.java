package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.ArrayList;
import java.util.List;

public class AdvisorStrategy implements MoveStrategy {
    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> paths = new ArrayList<>();

        Direction.orthogonalDirections()
                .forEach(direction -> addPath(paths, current, direction));
        if (current.isPalaceDiagonal()) {
            Direction.diagonalDirections()
                    .forEach(direction -> addPath(paths, current, direction));
        }

        return paths.stream()
                .filter(Path::isDestinationInsidePalace)
                .toList();
    }

    private void addPath(List<Path> paths, Position current, Direction direction) {
        direction.findNextPosition(current)
                .map(destination -> new Path(List.of(), destination))
                .ifPresent(paths::add);
    }
}
