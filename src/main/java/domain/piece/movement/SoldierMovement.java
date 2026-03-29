package domain.piece.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.Collections;
import java.util.List;

public final class SoldierMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        Direction forwardDirection = side.getForwardDirection();

        Intersection forward = forwardDirection.moveForward(from, MOVE_AMOUNT);
        Intersection left = forwardDirection.moveLeft(from, MOVE_AMOUNT);
        Intersection right = forwardDirection.moveRight(from, MOVE_AMOUNT);

        return List.of(
                new Path(forward, Collections.emptyList()),
                new Path(left, Collections.emptyList()),
                new Path(right, Collections.emptyList())
        );
    }
}
