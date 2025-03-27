package janggi.domain.movement.pathless;

import janggi.domain.movement.MoveStep;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class SaMovement extends InCastlePathlessMovement {

    public SaMovement() {
        super(Set.of(
            new MoveVector(MoveStep.LEFT),
            new MoveVector(MoveStep.RIGHT),
            new MoveVector(MoveStep.UP),
            new MoveVector(MoveStep.DOWN)
        ));
    }
}
