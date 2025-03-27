package janggi.domain.movement.path;

import janggi.domain.movement.InfiniteMoveProcess;
import janggi.domain.movement.MoveStep;
import java.util.Set;

public class ChaMovement extends PathMovement {

    public ChaMovement() {
        super(Set.of(
            new InfiniteMoveProcess(MoveStep.LEFT),
            new InfiniteMoveProcess(MoveStep.RIGHT),
            new InfiniteMoveProcess(MoveStep.UP),
            new InfiniteMoveProcess(MoveStep.DOWN)
        ));
    }
}
