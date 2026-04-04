package domain.strategy;

import domain.coordinate.Direction;
import domain.coordinate.Position;

import java.util.ArrayList;
import java.util.List;

public class PathBasedMoveStrategy implements MoveStrategy {

    private final List<List<Direction>> movePaths;

    public PathBasedMoveStrategy(List<List<Direction>> movePaths) {
        this.movePaths = List.copyOf(movePaths);
    }

    @Override
    public List<List<Direction>> calculatePotentialPaths(Position start) {
        List<List<Direction>> paths = new ArrayList<>();

        addBasicPotentialPaths(start, paths);

        return paths;
    }

    private void addBasicPotentialPaths(Position start, List<List<Direction>> paths) {
        for (List<Direction> movePath : movePaths) {
            List<Direction> directionPath = new ArrayList<>();

            Position current = start;
            for (Direction direction : movePath) {
                current = current.nextPosition(direction);

                directionPath.add(direction);
            }

            if (!current.isValidRange()) {
                continue;
            }

            paths.add(directionPath);
        }
    }
}
