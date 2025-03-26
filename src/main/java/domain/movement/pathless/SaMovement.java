package domain.movement.pathless;

import domain.movement.MoveUnit;
import domain.movement.MoveVector;
import java.util.Set;

public class SaMovement extends InCastlePathlessMovement {

    public SaMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.UP),
            new MoveVector(MoveUnit.DOWN)
        ));
    }
}
