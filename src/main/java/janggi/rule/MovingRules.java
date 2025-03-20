package janggi.rule;

import java.util.ArrayList;
import java.util.List;

public final class MovingRules {

    private final List<MovingRule> movingRules;

    public MovingRules(final List<MovingRule> movingRules) {
        this.movingRules = movingRules;
    }

    public List<MoveVector> getRuleVectors() {
        final List<MoveVector> positionDiffs = new ArrayList<>();
        for (MovingRule movingRule : movingRules) {
            positionDiffs.add(movingRule.sumUnit());
        }
        return positionDiffs;
    }

    public MovingRule findMatchRule(final MoveVector startEndDiff) {
        for (MovingRule movingRule : movingRules) {
            final MoveVector vectorSum = movingRule.sumUnit();
            if (vectorSum.equals(startEndDiff)) {
                return movingRule;
            }
        }
        throw new IllegalStateException();
    }
}
