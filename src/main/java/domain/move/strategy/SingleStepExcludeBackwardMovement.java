package domain.move.strategy;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.Collections;
import java.util.List;

public final class SingleStepExcludeBackwardMovement extends Movement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        Intersection forward = side.moveForward(from, MOVE_AMOUNT);
        Intersection left = side.moveLeft(from, MOVE_AMOUNT);
        Intersection right = side.moveRight(from, MOVE_AMOUNT);

        return List.of(
                new Path(forward, Collections.emptyList()),
                new Path(left, Collections.emptyList()),
                new Path(right, Collections.emptyList())
        );
    }
}
