package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class CannonStrategy implements MoveStrategy {

    private static final int CANNON_MIN_DISTANCE = 1;
    private static final int INITIAL_DISTANCE = 1;
    private static final int DISTANCE_INCREMENT = 1;

    @Override
    public List<Path> findMovablePaths(Position current) {
        List<Path> totalPaths = new ArrayList<>();
        List<Direction> directions = Direction.linear();

        for (Direction direction : directions) {
            totalPaths.addAll(collectPathsByDirection(current, direction));
        }

        if (current.isPalaceDiagonal()) {
            addDiagonalPath(totalPaths, current, Direction.diagonalDirections());
        }

        return Collections.unmodifiableList(totalPaths);
    }

    private void addDiagonalPath(List<Path> totalPaths, Position current, List<Direction> directions) {
        for (Direction direction : directions) {
            List<Path> diagonalPaths = collectPathsByDirection(current, direction).stream()
                    .filter(Path::isDestinationInsidePalace)
                    .toList();
            totalPaths.addAll(diagonalPaths);
        }
    }


    private List<Path> collectPathsByDirection(Position current, Direction direction) {
        List<Path> paths = new ArrayList<>();
        List<Position> route = new ArrayList<>();
        int currentDistance = INITIAL_DISTANCE;
        Optional<Position> next = direction.findNextPosition(current);

        while (next.isPresent()) {
            Position destination = next.get();
            addValidPath(paths, route, destination, currentDistance);

            route.add(destination);
            next = direction.findNextPosition(destination);
            currentDistance += DISTANCE_INCREMENT;
        }

        return paths;
    }

    private void addValidPath(List<Path> paths, List<Position> route, Position destination, int distance) {
        if (distance >= CANNON_MIN_DISTANCE) {
            paths.add(new Path(List.copyOf(route), destination));
        }
    }
}
