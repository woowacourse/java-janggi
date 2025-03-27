package janggi.domain.movement.path;

import static janggi.domain.movement.MoveStep.DOWN;
import static janggi.domain.movement.MoveStep.LEFT;
import static janggi.domain.movement.MoveStep.LEFT_DOWN;
import static janggi.domain.movement.MoveStep.LEFT_UP;
import static janggi.domain.movement.MoveStep.RIGHT;
import static janggi.domain.movement.MoveStep.RIGHT_DOWN;
import static janggi.domain.movement.MoveStep.RIGHT_UP;
import static janggi.domain.movement.MoveStep.UP;

import janggi.domain.movement.MoveProcess;
import java.util.Set;

public class SangMovement extends PathMovement {

    public SangMovement() {
        super(Set.of(
            new MoveProcess(LEFT, LEFT_UP, LEFT_UP),
            new MoveProcess(LEFT, LEFT_DOWN, LEFT_DOWN),
            new MoveProcess(RIGHT, RIGHT_UP, RIGHT_UP),
            new MoveProcess(RIGHT, RIGHT_DOWN, RIGHT_DOWN),
            new MoveProcess(UP, LEFT_UP, LEFT_UP),
            new MoveProcess(UP, RIGHT_UP, RIGHT_UP),
            new MoveProcess(DOWN, LEFT_DOWN, LEFT_DOWN),
            new MoveProcess(DOWN, RIGHT_DOWN, RIGHT_DOWN)
        ));
    }
}
