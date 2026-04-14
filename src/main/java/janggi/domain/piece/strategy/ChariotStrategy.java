package janggi.domain.piece.strategy;

import janggi.domain.Path;
import janggi.domain.JanggiPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChariotStrategy implements MoveStrategy {

    private static final int CHARIOT_MIN_DISTANCE = 1;
    private static final int INITIAL_DISTANCE = 1;
    private static final int DISTANCE_INCREMENT = 1;

    @Override
    public List<Path> findMovablePaths(JanggiPosition current) {
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

    private void addDiagonalPath(List<Path> totalPaths, JanggiPosition current, List<Direction> directions) {
        for (Direction direction : directions) {
            List<Path> diagonalPaths = collectPathsByDirection(current, direction).stream()
                    .filter(Path::isDestinationInsidePalace)
                    .toList();
            totalPaths.addAll(diagonalPaths);
        }
    }

    private List<Path> collectPathsByDirection(JanggiPosition current, Direction direction) {
        List<Path> paths = new ArrayList<>();
        List<JanggiPosition> route = new ArrayList<>();
        int currentDistance = INITIAL_DISTANCE;
        Optional<JanggiPosition> next = direction.findNextPosition(current);

        while (next.isPresent()) {
            JanggiPosition destination = next.get();
            addValidPath(paths, route, destination, currentDistance);

            route.add(destination);
            next = direction.findNextPosition(destination);
            currentDistance += DISTANCE_INCREMENT;
        }

        return paths;
    }

    private void addValidPath(List<Path> paths, List<JanggiPosition> route, JanggiPosition destination, int distance) {
        if (distance >= CHARIOT_MIN_DISTANCE) {
            paths.add(new Path(List.copyOf(route), destination));
        }
    }
}
