package domain.move.strategy;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public final class OrthogonalThenTwoDiagonalMovement extends Movement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        return Stream.of(forwardDirectionIntersections(from, side),
                        backWardDirectionIntersections(from, side),
                        leftDirectionIntersections(from, side),
                        rightDirectionIntersections(from, side))
                .flatMap(List::stream)
                .toList();
    }

    private static List<Path> forwardDirectionIntersections(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection forward = side.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = side.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardLeft2Times = side.moveForwardLeft(forwardLeft, MOVE_AMOUNT);

        Intersection forwardRight = side.moveForwardRight(forward, MOVE_AMOUNT);
        Intersection forwardRight2Times = side.moveForwardRight(forwardRight, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft2Times, List.of(forward, forwardLeft)));
        paths.add(new Path(forwardRight2Times, List.of(forward, forwardRight)));

        return List.copyOf(paths);
    }

    private static List<Path> backWardDirectionIntersections(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection backward = side.moveBackward(from, MOVE_AMOUNT);
        Intersection backwardLeft = side.moveBackwardLeft(backward, MOVE_AMOUNT);
        Intersection backwardLeft2Times = side.moveBackwardLeft(backwardLeft, MOVE_AMOUNT);

        Intersection backwardRight = side.moveBackwardRight(backward, MOVE_AMOUNT);
        Intersection backwardRight2Times = side.moveBackwardRight(backwardRight, MOVE_AMOUNT);

        paths.add(new Path(backwardLeft2Times, List.of(backward, backwardRight)));
        paths.add(new Path(backwardRight2Times, List.of(backward, backwardRight)));

        return List.copyOf(paths);
    }

    private static List<Path> leftDirectionIntersections(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection left = side.moveLeft(from, MOVE_AMOUNT);
        Intersection forwardLeft = side.moveBackwardLeft(left, MOVE_AMOUNT);
        Intersection forwardLeft2Times = side.moveBackwardLeft(forwardLeft, MOVE_AMOUNT);
        Intersection forwardRight = side.moveForwardLeft(left, MOVE_AMOUNT);
        Intersection forwardRight2Times = side.moveForwardLeft(forwardRight, MOVE_AMOUNT);

        paths.add(new Path(forwardLeft2Times, List.of(left, forwardLeft)));
        paths.add(new Path(forwardRight2Times, List.of(left, forwardRight)));

        return List.copyOf(paths);
    }

    private static List<Path> rightDirectionIntersections(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        Intersection right = side.moveRight(from, MOVE_AMOUNT);
        Intersection forwardRight = side.moveForwardRight(right, MOVE_AMOUNT);
        Intersection forwardRight2Times = side.moveForwardRight(forwardRight, MOVE_AMOUNT);

        Intersection backwardRight = side.moveBackwardRight(right, MOVE_AMOUNT);
        Intersection backwardRight2Times = side.moveBackwardRight(backwardRight, MOVE_AMOUNT);

        paths.add(new Path(forwardRight2Times, List.of(right, forwardRight)));
        paths.add(new Path(backwardRight2Times, List.of(right, backwardRight)));

        return List.copyOf(paths);
    }
}
