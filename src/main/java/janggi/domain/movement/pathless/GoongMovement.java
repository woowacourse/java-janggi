package janggi.domain.movement.pathless;

import janggi.domain.movement.MoveUnit;
import janggi.domain.movement.MoveVector;
import java.util.Set;

public class GoongMovement extends InCastlePathlessMovement {

    public GoongMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.UP),
            new MoveVector(MoveUnit.DOWN)
        ));
    }
}
