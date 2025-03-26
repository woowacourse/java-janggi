package janggi.domain.movement.path;

import janggi.domain.movement.InfiniteMoveVector;
import janggi.domain.movement.MoveUnit;
import java.util.Set;

public class ChaMovement extends PathMovement {

    public ChaMovement() {
        super(Set.of(
            new InfiniteMoveVector(MoveUnit.LEFT),
            new InfiniteMoveVector(MoveUnit.RIGHT),
            new InfiniteMoveVector(MoveUnit.UP),
            new InfiniteMoveVector(MoveUnit.DOWN)
        ));
    }
}
