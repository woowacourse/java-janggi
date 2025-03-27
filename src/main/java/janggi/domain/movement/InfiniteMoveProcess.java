package janggi.domain.movement;

import java.util.Iterator;
import java.util.List;

public class InfiniteMoveProcess extends MoveProcess {

    public InfiniteMoveProcess(final MoveStep moveStep) {
        super(List.of(moveStep));
    }

    @Override
    public Iterator<MoveStep> iterator() {
        return new InfiniteIterator(moveSteps.getFirst());
    }

    private static class InfiniteIterator implements Iterator<MoveStep> {

        private final MoveStep moveStep;

        public InfiniteIterator(final MoveStep moveStep) {
            this.moveStep = moveStep;
        }

        @Override
        public boolean hasNext() {
            return true;
        }

        @Override
        public MoveStep next() {
            return moveStep;
        }
    }
}
