package domain.move.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class OrthogonalThenDiagonalMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        return Stream.of(forwardDirectionPaths(from, side),
                        backWardDirectionPaths(from, side),
                        leftDirectionPaths(from, side),
                        rightDirectionPaths(from, side))
                .flatMap(List::stream)
                .toList();
    }

    private static List<Path> forwardDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();
        Direction forwardDirection = side.getForwardDirection();

        Intersection forward = forwardDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = forwardDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = forwardDirection.moveForwardRight(forward, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(forward)));
        paths.add(new Path(forwardRight, List.of(forward)));

        return List.copyOf(paths);
    }

    private static List<Path> backWardDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();
        Direction backwardDirection = side.getBackwardDirection();

        Intersection forward = backwardDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = backwardDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = backwardDirection.moveForwardRight(forward, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(forward)));
        paths.add(new Path(forwardRight, List.of(forward)));

        return List.copyOf(paths);
    }

    private static List<Path> leftDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();
        Direction leftDirection = side.getLeftDirection();

        Intersection forward = leftDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = leftDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = leftDirection.moveForwardRight(forward, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(forward)));
        paths.add(new Path(forwardRight, List.of(forward)));

        return List.copyOf(paths);
    }

    private static List<Path> rightDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();
        Direction rightDirection = side.getRightDirection();

        Intersection forward = rightDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = rightDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = rightDirection.moveForwardRight(forward, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(forward)));
        paths.add(new Path(forwardRight, List.of(forward)));

        return List.copyOf(paths);
    }
}
