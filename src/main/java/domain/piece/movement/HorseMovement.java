package domain.piece.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;
import java.util.stream.Stream;

public final class HorseMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Intersection> candidateIntersections(Intersection from, Side side) {
        return Stream.of(forwardDirectionIntersections(from, side),
                        backWardDirectionIntersections(from, side),
                        leftDirectionIntersections(from, side),
                        rightDirectionIntersections(from, side))
                .flatMap(List::stream)
                .toList();
    }

    private static List<Intersection> forwardDirectionIntersections(Intersection from, Side side) {
        Direction forwardDirection = side.getForwardDirection();
        Intersection forward = forwardDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = forwardDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = forwardDirection.moveForwardRight(forward, MOVE_AMOUNT);

        return List.of(forwardLeft, forwardRight);
    }

    private static List<Intersection> backWardDirectionIntersections(Intersection from, Side side) {
        Direction backwardDirection = side.getBackwardDirection();
        Intersection forward = backwardDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = backwardDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = backwardDirection.moveForwardRight(forward, MOVE_AMOUNT);

        return List.of(forwardLeft, forwardRight);
    }

    private static List<Intersection> leftDirectionIntersections(Intersection from, Side side) {
        Direction leftDirection = side.getLeftDirection();
        Intersection forward = leftDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = leftDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = leftDirection.moveForwardRight(forward, MOVE_AMOUNT);

        return List.of(forwardLeft, forwardRight);
    }

    private static List<Intersection> rightDirectionIntersections(Intersection from, Side side) {
        Direction rightDirection = side.getRightDirection();
        Intersection forward = rightDirection.moveForward(from, MOVE_AMOUNT);
        Intersection forwardLeft = rightDirection.moveForwardLeft(forward, MOVE_AMOUNT);
        Intersection forwardRight = rightDirection.moveForwardRight(forward, MOVE_AMOUNT);

        return List.of(forwardLeft, forwardRight);
    }
}
