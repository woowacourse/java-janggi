package janggi.domain.movement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MoveVector implements Iterable<MoveStep> {

    protected final List<MoveStep> moveSteps;

    public MoveVector(final List<MoveStep> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public MoveVector(final MoveStep... moveSteps) {
        this.moveSteps = Arrays.asList(moveSteps);
    }

    public MoveStep first() {
        return moveSteps.getFirst();
    }

    @Override
    public Iterator<MoveStep> iterator() {
        return moveSteps.iterator();
    }
}
