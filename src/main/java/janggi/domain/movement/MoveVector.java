package janggi.domain.movement;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class MoveVector implements Iterable<MoveUnit> {

    protected final List<MoveUnit> moveUnits;

    public MoveVector(final List<MoveUnit> moveUnits) {
        this.moveUnits = moveUnits;
    }

    public MoveVector(final MoveUnit... moveUnits) {
        this.moveUnits = Arrays.asList(moveUnits);
    }

    public MoveUnit first() {
        return moveUnits.getFirst();
    }

    @Override
    public Iterator<MoveUnit> iterator() {
        return moveUnits.iterator();
    }
}
