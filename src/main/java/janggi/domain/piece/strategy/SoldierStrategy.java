package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SoldierStrategy implements MoveStrategy {
    private final Direction forwardDirection;

    public SoldierStrategy(Direction forwardDirection) {
        this.forwardDirection = forwardDirection;
    }

    @Override
    public List<Path> findMovablePaths(JanggiPosition current) {
        List<Path> paths = new ArrayList<>();

        addPath(paths, current, forwardDirection);
        addPath(paths, current, Direction.west());
        addPath(paths, current, Direction.east());

        if (current.isPalaceDiagonal()) {
            paths.addAll(calculateDiagonalPath(current));
        }

        return Collections.unmodifiableList(paths);
    }

    private List<Path> calculateDiagonalPath(JanggiPosition current) {
        List<Path> diagonalDirections = new ArrayList<>();
        Direction.diagonalDirections()
                .stream()
                .filter(forwardDirection::isSameDirectionOfProgress)
                .forEach(direction -> addPath(diagonalDirections, current, direction));
        return diagonalDirections.stream()
                .filter(Path::isDestinationInsidePalace)
                .toList();
    }

    private void addPath(List<Path> paths, JanggiPosition current, Direction direction) {
        direction.findNextPosition(current)
                .map(position -> new Path(List.of(), position))
                .ifPresent(paths::add);
    }
}
