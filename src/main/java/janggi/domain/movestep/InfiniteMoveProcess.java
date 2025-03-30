package janggi.domain.movestep;

import java.util.Iterator;
import java.util.List;

public class InfiniteMoveProcess extends MoveProcess {

    public InfiniteMoveProcess(final MoveStep moveStep) {
        super(List.of(moveStep));
    }

    @Override
    public Iterator<MoveStep> iterator() {
        return new Iterator<>() {

            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public MoveStep next() {
                return moveSteps.getFirst();
            }
        };
    }
}
