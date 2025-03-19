package janggi;

import java.util.List;

public final class MovingRule {

    private final List<MoveVector> movingRule;

    public MovingRule(final List<MoveVector> movingRule) {
        this.movingRule = movingRule;
    }

    public MoveVector sumUnit() {
        MoveVector sumUnit = new MoveVector(0, 0);
        for (MoveVector moveVector : movingRule) {
            sumUnit = sumUnit.add(moveVector);
        }
        return sumUnit;
    }
}
