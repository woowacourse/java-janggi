package janggi.domain.movestep;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MoveProcess implements Iterable<MoveStep> {

    protected final List<MoveStep> moveSteps;

    public MoveProcess(final List<MoveStep> moveSteps) {
        this.moveSteps = moveSteps;
    }

    public MoveProcess(final MoveStep... moveSteps) {
        this.moveSteps = Arrays.asList(moveSteps);
    }

    @Override
    public Iterator<MoveStep> iterator() {
        return moveSteps.iterator();
    }
}
