package janggi.domain.movement;

import java.util.Iterator;
import java.util.List;

public class InfiniteMoveVector extends MoveVector {

    public InfiniteMoveVector(final MoveStep moveStep) {
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
                return first();
            }
        };
    }
}
