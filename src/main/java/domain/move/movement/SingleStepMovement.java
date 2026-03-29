package domain.move.movement;

import domain.board.Intersection;
import domain.direction.Direction;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class SingleStepMovement extends PieceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();
        for (Direction direction : side.getAllDirections()) {
            Intersection destination = direction.moveForward(from, MOVE_AMOUNT);
            paths.add(new Path(destination, Collections.emptyList()));
        }

        return List.copyOf(paths);
    }
}
