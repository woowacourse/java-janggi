package domain.movement.path;

import static domain.movement.MoveUnit.DOWN;
import static domain.movement.MoveUnit.LEFT;
import static domain.movement.MoveUnit.LEFT_DOWN;
import static domain.movement.MoveUnit.LEFT_UP;
import static domain.movement.MoveUnit.RIGHT;
import static domain.movement.MoveUnit.RIGHT_DOWN;
import static domain.movement.MoveUnit.RIGHT_UP;
import static domain.movement.MoveUnit.UP;

import domain.movement.MoveVector;
import java.util.Set;

public class SangMovement extends PathMovement {

    public SangMovement() {
        super(Set.of(
            new MoveVector(LEFT, LEFT_UP, LEFT_UP),
            new MoveVector(LEFT, LEFT_DOWN, LEFT_DOWN),
            new MoveVector(RIGHT, RIGHT_UP, RIGHT_UP),
            new MoveVector(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            new MoveVector(UP, LEFT_UP, LEFT_UP),
            new MoveVector(UP, RIGHT_UP, RIGHT_UP),
            new MoveVector(DOWN, LEFT_DOWN, LEFT_DOWN),
            new MoveVector(DOWN, RIGHT_DOWN, RIGHT_DOWN)
        ));
    }
}
