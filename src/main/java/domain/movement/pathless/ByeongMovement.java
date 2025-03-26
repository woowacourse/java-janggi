package domain.movement.pathless;

import domain.movement.MoveUnit;
import domain.movement.MoveVector;
import java.util.Set;

public class ByeongMovement extends PathlessMovement {

    public ByeongMovement() {
        super(Set.of(
            new MoveVector(MoveUnit.LEFT),
            new MoveVector(MoveUnit.RIGHT),
            new MoveVector(MoveUnit.DOWN)
        ));
    }
}
