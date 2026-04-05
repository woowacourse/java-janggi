package domain.move.strategy;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.List;

public final class SingleStepExcludeBackwardMovement extends Movement implements PalaceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    private final Movement baseMovement = new SingleStepMovement();

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        List<Path> allDirectionPaths = new ArrayList<>(baseMovement.candidatePaths(from, side));

        List<Path> palaceDiagonalPaths = PalaceMovement.super.palaceDiagonalPaths(from);
        allDirectionPaths.addAll(palaceDiagonalPaths);

        return allDirectionPaths.stream()
                .distinct()
                .filter(path -> isNotBackward(from, path.destination(), side))
                .toList();
    }

    private boolean isNotBackward(Intersection from, Intersection to, Side side) {
        Intersection backward = side.moveBackward(from, MOVE_AMOUNT);

        return backward.row() != to.row();
    }
}
