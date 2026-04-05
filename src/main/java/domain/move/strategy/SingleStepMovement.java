package domain.move.strategy;

import domain.board.Intersection;
import domain.direction.MoveAmount;
import domain.game.Side;
import domain.move.Path;
import java.util.ArrayList;
import java.util.List;

public final class SingleStepMovement extends Movement implements PalaceMovement {

    private static final MoveAmount MOVE_AMOUNT = new MoveAmount(1);

    @Override
    protected List<Path> candidatePaths(Intersection from, Side side) {
        List<Path> paths = new ArrayList<>();

        List<Intersection> destinations = List.of(
                side.moveForward(from, MOVE_AMOUNT),
                side.moveLeft(from, MOVE_AMOUNT),
                side.moveRight(from, MOVE_AMOUNT),
                side.moveBackward(from, MOVE_AMOUNT)
        );

        for (Intersection destination : destinations) {
            paths.add(Path.of(destination));
        }

        List<Path> palaceDiagonalPaths = PalaceMovement.super.palaceDiagonalPaths(from);
        paths.addAll(palaceDiagonalPaths);

        return List.copyOf(paths);
    }
}
