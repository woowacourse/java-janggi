package janggi.domain.movement.path;

import static janggi.domain.movement.MoveStep.DOWN;
import static janggi.domain.movement.MoveStep.LEFT;
import static janggi.domain.movement.MoveStep.LEFT_DOWN;
import static janggi.domain.movement.MoveStep.LEFT_UP;
import static janggi.domain.movement.MoveStep.RIGHT;
import static janggi.domain.movement.MoveStep.RIGHT_DOWN;
import static janggi.domain.movement.MoveStep.RIGHT_UP;
import static janggi.domain.movement.MoveStep.UP;

import janggi.domain.movement.MoveVector;
import java.util.Set;

public class MaMovement extends PathMovement {

    public MaMovement() {
        super(Set.of(
            new MoveVector(LEFT, LEFT_UP),
            new MoveVector(LEFT, LEFT_DOWN),
            new MoveVector(RIGHT, RIGHT_UP),
            new MoveVector(RIGHT, RIGHT_DOWN),
            new MoveVector(UP, LEFT_UP),
            new MoveVector(UP, RIGHT_UP),
            new MoveVector(DOWN, LEFT_DOWN),
            new MoveVector(DOWN, RIGHT_DOWN)
        ));
    }
}
