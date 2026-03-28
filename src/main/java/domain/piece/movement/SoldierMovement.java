package domain.piece.movement;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import java.util.List;

public final class SoldierMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Intersection> candidateIntersections(Intersection from, Side side) {
        Intersection forward = side.getForwardDirection().moveForward(from, MOVE_AMOUNT);
        Intersection left = side.getForwardDirection().moveLeft(from, MOVE_AMOUNT);
        Intersection right = side.getForwardDirection().moveRight(from, MOVE_AMOUNT);

        return List.of(forward, left, right);
    }
}
