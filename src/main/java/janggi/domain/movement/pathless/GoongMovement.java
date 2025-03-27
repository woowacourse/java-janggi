package janggi.domain.movement.pathless;

import janggi.domain.movement.MoveStep;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class GoongMovement extends InCastlePathlessMovement {

    public GoongMovement() {
        super(Set.of(
            new MoveVector(MoveStep.LEFT),
            new MoveVector(MoveStep.RIGHT),
            new MoveVector(MoveStep.UP),
            new MoveVector(MoveStep.DOWN)
        ));
    }
}
