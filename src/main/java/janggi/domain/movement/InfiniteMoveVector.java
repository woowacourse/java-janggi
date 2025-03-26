package janggi.domain.movement;

import java.util.Iterator;
import java.util.List;

public class InfiniteMoveVector extends MoveVector {

    public InfiniteMoveVector(final MoveUnit moveUnit) {
        super(List.of(moveUnit));
    }

    @Override
    public Iterator<MoveUnit> iterator() {
        return new Iterator<>() {
            @Override
            public boolean hasNext() {
                return true;
            }

            @Override
            public MoveUnit next() {
                return first();
            }
        };
    }
}
