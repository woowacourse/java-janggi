package janggi.domain.movement.path;

import janggi.domain.movement.InfiniteMoveVector;
import janggi.domain.movement.MoveStep;
import java.util.Set;

public class ChaMovement extends PathMovement {

    public ChaMovement() {
        super(Set.of(
            new InfiniteMoveVector(MoveStep.LEFT),
            new InfiniteMoveVector(MoveStep.RIGHT),
            new InfiniteMoveVector(MoveStep.UP),
            new InfiniteMoveVector(MoveStep.DOWN)
        ));
    }
}
