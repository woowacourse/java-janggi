package janggi.domain.movement.pathless;

import janggi.domain.movement.MoveStep;
import janggi.domain.movement.MoveProcess;
import java.util.Set;

public class SaMovement extends InCastlePathlessMovement {

    public SaMovement() {
        super(Set.of(
            new MoveProcess(MoveStep.LEFT),
            new MoveProcess(MoveStep.RIGHT),
            new MoveProcess(MoveStep.UP),
            new MoveProcess(MoveStep.DOWN)
        ));
    }
}
