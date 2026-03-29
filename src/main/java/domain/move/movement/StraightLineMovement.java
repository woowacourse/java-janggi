package domain.move.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.List;

public final class StraightLineMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        return allDirectionPaths(from, side);
    }

    private List<Path> allDirectionPaths(Intersection from, Side side) {
        return side.getAllDirections()
                .stream()
                .map(direction -> allPathsOfDirection(from, direction))
                .flatMap(List::stream)
                .toList();
    }

    private List<Path> allPathsOfDirection(Intersection from, Direction direction) {
        List<Path> paths = new ArrayList<>();
        List<Intersection> passingIntersections = new ArrayList<>();

        Intersection current = direction.moveForward(from, MOVE_AMOUNT);
        while (current.isInBounds()) {
            paths.add(new Path(current, List.copyOf(passingIntersections)));
            passingIntersections.add(current);
            current = direction.moveForward(current, MOVE_AMOUNT);
        }

        return List.copyOf(paths);
    }
}
