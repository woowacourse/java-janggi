package domain.move.strategy;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class OrthogonalThenDiagonalMovement extends Movement {

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

        Intersection forward = side.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = side.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = side.moveForwardRight(forward, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(forward)));
        paths.add(new Path(forwardRight, List.of(forward)));

        return List.copyOf(paths);
    }

    private static List<Path> backWardDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection backward = side.moveBackward(from, MOVE_AMOUNT);
        Intersection backwardLeft = side.moveBackwardLeft(backward, MOVE_AMOUNT);
        Intersection backwardRight = side.moveBackwardRight(backward, MOVE_AMOUNT);

        paths.add(new Path(backwardLeft, List.of(backward)));
        paths.add(new Path(backwardRight, List.of(backward)));

        return List.copyOf(paths);
    }

    private static List<Path> leftDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        // TODO 여기도 방향이 헷갈림
        Intersection left = side.moveLeft(from, MOVE_AMOUNT);
        Intersection forwardLeft = side.moveBackwardLeft(left, MOVE_AMOUNT);
        Intersection forwardRight = side.moveForwardLeft(left, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft, List.of(left)));
        paths.add(new Path(forwardRight, List.of(left)));

        return List.copyOf(paths);
    }

    private static List<Path> rightDirectionPaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection right = side.moveRight(from, MOVE_AMOUNT);
        Intersection forwardRight = side.moveForwardRight(right, MOVE_AMOUNT);
        Intersection backwardRight = side.moveBackwardRight(right, MOVE_AMOUNT);

        paths.add(new Path(forwardRight, List.of(right)));
        paths.add(new Path(backwardRight, List.of(right)));

        return List.copyOf(paths);
    }
}
